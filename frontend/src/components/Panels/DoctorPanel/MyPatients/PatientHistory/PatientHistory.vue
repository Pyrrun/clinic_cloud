<template>
    <v-card min-height="850px">
        <v-card-title>Patient medical history</v-card-title>
        <v-card-subtitle class="mt-1">In this card you can browse through medical history and previous visits of your patient</v-card-subtitle>
        <v-img src="https://anmj.org.au/wp-content/uploads/2020/03/Older-patient-specialling-in-acute-MAIN-WEB.jpg" height="300px">
        </v-img>
        <v-card-title>{{patientData.firstName}} {{patientData.secondName}} {{patientData.lastName}}</v-card-title>
        <v-card-subtitle>Patient</v-card-subtitle>
        <v-card-text class="text-center" v-if="visitChosen">
            <v-btn color="primary" large  @click="confirmDialogOpen = true">Begin visit</v-btn>
        </v-card-text>
        <v-tabs centered v-model="tabIndex">
            <v-tab @click="tabIndex = 0">Patient details</v-tab>
            <v-tab @click="tabIndex = 1">All visits</v-tab>
            <v-tab @click="tabIndex = 2" v-if="role === 'DOCTOR'">My visits</v-tab>
        </v-tabs>
        <v-tabs-items v-model="tabIndex">
            <v-tab-item>
                <PatientDetails :isEditable="false" :patientData="patientData"></PatientDetails>
            </v-tab-item>
            <v-tab-item>
                <VisitTable :patientData="patientData" :tabIndex="tabIndex"></VisitTable>
            </v-tab-item>
            <v-tab-item>
                <VisitTable :patientData="patientData" :tabIndex="tabIndex"></VisitTable>
            </v-tab-item>
        </v-tabs-items>

        <v-dialog v-model="confirmDialogOpen" max-width="400">
            <v-card>
                <v-card-title>
                    Visit begin confirmation
                </v-card-title>
                <v-card-text>
                    Are you sure you want to begin your visit with <b>{{patientData.firstName}} {{patientData.secondName}} {{patientData.lastName}}</b>?
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="confirmDialogOpen = false">
                        Cancel
                    </v-btn>
                    <v-btn color="blue darken-1" text @click="beginVisit">Yes,
                        I'm sure
                    </v-btn>
                    <v-spacer></v-spacer>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-card>
</template>

<script>
import VisitTable from "@/components/Panels/DoctorPanel/MyPatients/PatientHistory/VisitTable";
import PatientDetails from "@/components/Panels/DoctorPanel/MyPatients/PatientDetails";
export default {
    name: "PatientHistory",
    components: {VisitTable, PatientDetails},
    props:{
        patientData: Object,
        visitChosen: Boolean,
    },
    data(){
        return{
            tabIndex: 0,
            confirmDialogOpen: false,
            role: localStorage.getItem('role')

        }
    },
    methods:{
        beginVisit(){
            this.confirmDialogOpen = false
            this.$emit('visitStarted')
        }

    }
}
</script>

<style scoped>

</style>