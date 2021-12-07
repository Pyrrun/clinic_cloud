<template>
    <v-container fluid>
        <v-row justify="center">
            <v-col cols="12" lg="10">
                <v-card>
                    <v-card-title class="display-2">
                        Patient table
                    </v-card-title>
                    <v-card-subtitle>
                        Manage your patients
                    </v-card-subtitle>
                    <v-card-subtitle>
                        <v-row justify="space-between">
                            <v-col cols="5">
                                <v-text-field
                                    v-model="search"
                                    append-icon="mdi-magnify"
                                    label="Search"
                                    single-line
                                    hide-details
                                ></v-text-field>
                            </v-col>
                            <v-col cols="7" align="end">
                                <v-dialog max-width="400"
                                          v-model="newPatientDialog">
                                    <template v-slot:activator="on" v-on="on">
                                        <v-btn @click="startPatientAdding" color="primary">New patient</v-btn>
                                    </template>
                                    <template>
                                        <v-card>
                                            <v-card-title>
                                                Add new patient to your table
                                            </v-card-title>
                                            <v-card-actions>
                                                <PatientSearchComponent
                                                    v-if="newPatientDialog"
                                                    @patientSelected="selectPatientToAdd"
                                                >
                                                </PatientSearchComponent>
                                            </v-card-actions>
                                            <v-card-text align="end">
                                                <v-dialog max-width="300" v-model="warningDialog">
                                                    <template v-slot:activator="on" v-on="on">
                                                        <v-btn
                                                            color="primary"
                                                            @click="addNewPatient"
                                                            :disabled="!patientSelected"
                                                        >Add
                                                        </v-btn>
                                                    </template>
                                                        <v-card>
                                                            <v-card-title>Warning</v-card-title>
                                                            <v-card-text>Patient <b>{{ patientData.firstName }}
                                                                {{ patientData.secondName }}
                                                                {{ patientData.lastName }}</b> is already a
                                                                patient of doctor <b>{{ doctorName }}</b>. Are you sure
                                                                you want to place him as your patient?
                                                            </v-card-text>
                                                            <v-card-actions>
                                                                <v-spacer></v-spacer>
                                                                <v-btn color="blue darken-1" text
                                                                       @click="warningDialog = false">
                                                                    Cancel
                                                                </v-btn>
                                                                <v-btn color="blue darken-1" text
                                                                       @click="submitNewPatient">Yes,
                                                                    I'm sure
                                                                </v-btn>
                                                                <v-spacer></v-spacer>
                                                            </v-card-actions>
                                                        </v-card>
                                                </v-dialog>
                                            </v-card-text>
                                        </v-card>
                                    </template>
                                </v-dialog>
                            </v-col>
                        </v-row>
                    </v-card-subtitle>
                    <v-dialog max-width="500" v-model="patientDetails">
                        <PatientDetailsCard :patientData="patientData"></PatientDetailsCard>
                    </v-dialog>
                    <v-dialog v-model="historyDialog" max-width="700">
                        <PatientHistory :patientData="patientData"></PatientHistory>
                    </v-dialog>
                    <v-dialog v-model="deleteDialog" max-width="500">
                        <PatientDelete
                            @deleteDialogClosed="deleteDialog = false"
                            @patientDeleteSubmitted="deletePatient"
                            :patientData="patientData"
                        ></PatientDelete>
                    </v-dialog>
                    <v-data-table
                        :headers="headers"
                        :items="patients"
                        :single-select="singleSelect"
                        item-key="name"
                        :search="search"
                    >
                        <template v-slot:item="{ item }">
                            <tr @click.stop="showPatientDetails(item)">
                                <td>{{ item.firstName }}</td>
                                <td class="text-xs-right">{{ item.secondName }}</td>
                                <td class="text-xs-right">{{ item.lastName }}</td>
                                <td class="text-xs-right">{{ item.pesel }}</td>
                                <td class="text-xs-right">{{ item.birthdate }}</td>
                                <td class="text-xs-right">{{ item.phone }}</td>
                                <td class="text-xs-right">{{ item.email }}</td>
                                <td class="text-xs-right">{{ item.address.street }}</td>
                                <td class="text-xs-right">{{ item.address.flatNumber }}</td>
                                <td class="text-xs-right">{{ item.address.postcode }}</td>
                                <td class="text-xs-right">{{ item.address.city }}</td>
                                <td class="text-xs-right">
                                    <v-btn color="primary" x-small @click.stop="openHistoryDialog(item)">Check</v-btn>
                                </td>
                                <td v-if="role === 'DOCTOR'">
                                    <v-icon
                                        @click.stop="openDeleteDialog(item)"
                                        color="primary"
                                    >
                                        mdi-delete
                                    </v-icon>
                                </td>
                            </tr>
                        </template>
                    </v-data-table>
                    <v-card-actions>
                        <v-btn text color="primary" @click="goBack">Return</v-btn>
                    </v-card-actions>

                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import {tokenAxios} from "@/axios";
import PatientDelete from "@/components/Panels/DoctorPanel/MyPatients/PatientDelete";
import PatientHistory from "@/components/Panels/DoctorPanel/MyPatients/PatientHistory/PatientHistory";
import PatientSearchComponent from "@/components/Panels/ReceptionPanel/NewVisit/PatientSearchComponent";
import PatientDetailsCard from "@/components/Panels/DoctorPanel/MyPatients/PatientDetailsCard";


export default {
    name: "PatientTable",
    components: {PatientDelete, PatientHistory, PatientDetailsCard, PatientSearchComponent},
    data() {
        return {
            newPatientDialog: false,
            findPatient: '',
            patientDetails: false,
            search: '',
            singleSelect: true,
            patients: [],
            patientData: '',
            deleteDialog: false,
            warningDialog: false,
            historyDialog: false,
            doctorName: '',
            patientSelected: false,
            role: localStorage.getItem('role')
        }

    },
    methods: {

        startPatientAdding(){
          if(localStorage.getItem('role') === 'DOCTOR'){
              this.newPatientDialog = true
              return
          }
          this.$emit('newPatientClicked')


        },
        selectPatientToAdd(patient, patientSelected) {
            this.patientData = patient
            this.patientSelected = patientSelected
            this.patients.filter((element) => {
                if (element.pesel === this.patientData.pesel) {
                    this.patientSelected = false
                }
            })
        },
        openHistoryDialog(item) {
            this.patientData = item
            this.historyDialog = true
        },
        showPatientDetails(item) {
            this.patientData = item
            this.patientDetails = true
        },
        goBack() {
            this.$emit('returnClicked')
        },
        openDeleteDialog(item) {
            this.patientData = item
            this.deleteDialog = true
        },

        submitNewPatient() {
            tokenAxios.patch('patients/' + this.patientData.id + '/add-doctor', {}, {
                params: {
                    doctorId: localStorage.getItem('id')
                }
            }).then(() => {
                this.patients.push(this.patientData)
            }).finally(() => {
                this.newPatientDialog = false
                this.warningDialog = false
                this.$toast.success('Patient added successfully')
            })
        },

        addNewPatient() {
            if (this.patientData.doctorId) {
                tokenAxios.get('doctors/' + this.patientData.doctorId + '/name')
                    .then((response) => {
                        this.doctorName = response.data
                    })
                    .finally(() => {
                        this.newPatientDialog = false
                        this.warningDialog = true
                    })
                return
            }
            this.submitNewPatient()


        },
        deletePatient() {
            tokenAxios.patch('patients/' + this.patientData.id + '/remove-doctor', {}, {
                    params: {
                        doctorId: localStorage.getItem('id')
                    }
                }
            ).then(() => {
                this.patients.splice(this.patients.indexOf(this.patientData), 1)
                this.$toast.success('Patient removed successfully from your table')
            }).finally(() => {
                this.deleteDialog = false
            })
        }


    },

    computed: {
        headers() {
            let headers = [
                {
                    text: 'First name',
                    align: 'start',
                    sortable: true,
                    value: 'firstName',
                },
                {
                    text: 'Second name',
                    value: 'secondName',

                },
                {text: 'Last name', value: 'lastName'},
                {text: 'PESEL', value: 'pesel'},
                {text: 'Date of birth', value: 'birthdate'},
                {text: 'Phone', value: 'phone'},
                {text: 'E-mail', value: 'email'},
                {text: 'Street', value: 'street'},
                {text: 'Flat number', value: 'flatNumber'},
                {text: 'Post code', value: 'postcode'},
                {text: 'City', value: 'city'},
                {text: 'History', value: 'history', sortable: false},


            ]
            if(this.role === 'DOCTOR'){
                headers.push( {text: 'Actions', value: 'actions', sortable: false})
            }
            return headers
        },

    },

    created() {
        if(this.role === 'DOCTOR'){
            tokenAxios.get('patients/', {
                params: {
                    doctorId: localStorage.getItem('id')
                }
            }).then((response) => {
                response.data.filter((element) => {
                    this.patients.push(
                        {
                            id: element.id,
                            firstName: element.firstName,
                            secondName: element.secondName,
                            lastName: element.lastName,
                            pesel: element.pesel,
                            birthdate: element.birthdate,
                            phone: element.phone,
                            email: element.email,
                            address: element.address
                        }
                    )
                })
            })
            return
        }
        tokenAxios.get('patients')
            .then((response) => {
                console.log(response.data)
                response.data.forEach((element) => {
                    this.patients.push(
                        {
                            id: element.id,
                            firstName: element.firstName,
                            secondName: element.secondName,
                            lastName: element.lastName,
                            pesel: element.pesel,
                            birthdate: element.birthdate,
                            phone: element.phone,
                            email: element.email,
                            address: element.address
                        }
                    )
                })
            })

    }
}
</script>

<style scoped>

</style>