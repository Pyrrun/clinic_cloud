<template>
    <div>
    <div class="text-center">
        <h1 class="display-1 font-weight-thin mb-3">
            <b>Step 4</b>
        </h1>
        <h2 class="display-1 font-weight-thin mb-3">
            <i>Medicine</i>
        </h2>
        <v-card-text>
            Please enter the name of the medicine you would like to find below
        </v-card-text>
    </div>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="addNewMedication">New medication</v-btn>
            <v-btn color="error" :disabled="!firstMedicationSearchStarted" @click="cancelMedication">
                <v-icon>
                    mdi-delete
                </v-icon>
            </v-btn>
        </v-card-actions>
        <v-tabs  v-model="medicationIndex">
            <v-tab :disabled="!firstMedicationSearchStarted"
                   v-for="(medication, i) in medications"
                   :key="i"
            >
                Medication {{ i + 1 }}
            </v-tab>
        </v-tabs>
        <v-card-text>
            <v-autocomplete
                :disabled="!firstMedicationSearchStarted"
                placeholder="Medication name"
                prepend-icon="mdi-database-search"
                :search-input.sync="search"
                :loading="loading"
                :items="items"
                label="Medication"
            >
            </v-autocomplete>
        </v-card-text>
        <v-tabs-items v-model="medicationIndex">
            <v-tab-item v-for="(medication, i) in medications"
                        :key="i">
                <div v-if="medication.name">
                    <v-card-text>
                        <v-row>
                            <v-col cols="4">
                                <v-img :src="imgDir + medication.imageName" ></v-img>
                            </v-col>
                            <v-col    cols="8" class="text-center">
                                <span class="display-2">{{medication.name}}</span><br>
                                <span class="body-1">{{medication.description}}</span><br>
                            </v-col>
                        </v-row>
                    </v-card-text>
                    <v-card-text>
                        <v-col cols="4">
                       <v-text-field
                           :disabled="!firstMedicationSearchStarted"
                           label="Quantity*"
                           v-model="quantity"
                           :rules="[(v) => v < 100 || 'The maximum quantity is 99']"
                       ></v-text-field>
                        </v-col>
                    </v-card-text>
                    <v-card-text>
                        The medication you have selected is <b>{{medication.name}}</b>. In the text area below you can include the useful information connected with taking this product.
                    </v-card-text>

                    <v-card-actions>
                        <v-textarea  v-model="medicationDetails"
                                     solo
                                     :disabled="!firstMedicationSearchStarted"
                                     placeholder="Usage information, eg. dosage">
                        </v-textarea>
                    </v-card-actions>
    </div>
            </v-tab-item>
        </v-tabs-items>
        <v-card-actions>
            <v-btn color="primary" @click="goBack" text>Return</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="saveStep4"  text>Save</v-btn>
        </v-card-actions>
        <v-dialog max-width="600px" v-model="visitDetailsDialog">
                <VisitDetails @visitFinished="closeVisitDetailsDialog" :visitData="visitData" :isVisitBeingCreated="true"></VisitDetails>
        </v-dialog>
    </div>


</template>

<script>
import {tokenAxios} from "@/axios";
import VisitDetails from "@/components/Panels/DoctorPanel/MyPatients/PatientHistory/VisitDetails";


export default {
    name: "Step4",
    components: {VisitDetails},

    props: {
        visitData: Object
    },

    data(){
        return{
            visitDetailsDialog: false,
            medicationIndex: 0,
            firstMedicationSearchStarted: false,
            loading: false,
            items: [],
            imgDir: `${process.env.VUE_APP_BASE_URL}/images/medications/`,
            medications: [{name: '', description: '', imageName: '', details: '', quantity: '', id: ''}],
            medicationFound: false,
            medication: null,
            search: '',
            medicationDetails: '',
            foundMedications: [],
            quantity: ''
        }
    },
    watch: {
        search(val) {
            this.loading = true
            this.findMedicationDebounced(val)
        },



        medicationDetails(val){
            this.medications[this.medicationIndex].details = val
        },

        quantity(val){
            this.medications[this.medicationIndex].quantity = val
        },

        medicationIndex(){
            this.medicationDetails = this.medications[this.medicationIndex].details
            this.quantity = this.medications[this.medicationIndex].quantity
        }

    },
    methods: {
        goBack() {
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)
            setTimeout(() => {
                this.$emit('goBack')
            }, 200)
        },
        findMedicationDebounced(val) {
            clearTimeout(this._searchTimerId)
            this._searchTimerId = setTimeout(() => {
                this.findMedication(val)
            }, 200)

        },

        saveStep4(){
            let medication = this.medications.some((medication) => {
                return !medication.quantity && this.firstMedicationSearchStarted
            })
            if(medication){
                this.$toast.error('No quantity was specified in one of the medicaments')
                return
            }
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)
            setTimeout(() => {
                this.visitDetailsDialog = true
                let medications = []
                if(!this.firstMedicationSearchStarted){
                    this.visitData.medications = medications
                    return
                }
                this.visitData.medications = this.medications
            }, 300)


        },

        closeVisitDetailsDialog(){
            this.visitDetailsDialog = false
            this.$emit('visitFinished')
        },

       addNewMedication(){
           if(!this.firstMedicationSearchStarted){
               this.firstMedicationSearchStarted = true
               return
           }
           let medication = {name: '', description: '', imageName: '', details: '', quantity: '', id: ''}
           this.medicationFound = false
           this.medications.push(medication)
           this.medicationIndex = this.medications.indexOf(medication)
       },




        cancelMedication(){
            if(this.medications.length === 1){
                this.firstMedicationSearchStarted = false
                return
            }
            this.medications.splice(this.medicationIndex, 1)
        },

        findMedication(val) {
            this.medicationFound = false
            if (!val) {
                this.items = []
                this.loading = false
                return
            }
            tokenAxios.get('medications', {
               params: {
                   name: val
               }
           }).then((response) => {
               if(response.data.length === 0){
                   return
               }
               response.data.filter((element) => {
                   if (!this.items.includes(element.name)) {
                       this.items.push(element.name)
                       this.foundMedications.push(element)
                   }
               })
           }).finally(() => {
               this.loading = false
               let medication = this.medications[this.medicationIndex]
               if(this.items.includes(val)){
                   this.medicationFound = true
                   let newMedication = this.foundMedications.find((element) => {
                       if(element.name === val){
                           return element
                       }
                   })
                   medication.name = newMedication.name
                   medication.description = newMedication.description
                   medication.imageName = newMedication.imageName
                   medication.id = newMedication.id
               }
            else{
                   medication.name = ''
               }
           })

        }

    }
}
</script>

<style scoped>

</style>