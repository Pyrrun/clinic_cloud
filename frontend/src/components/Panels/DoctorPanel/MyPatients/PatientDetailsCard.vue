<template>
    <v-card>
        <v-img src="https://www.satoeurope.com/images/markets/patient-positive-identification-4.jpg"
               height="300px">
        </v-img>
        <v-card-title>{{patientData.firstName}} {{patientData.secondName}} {{patientData.lastName}}</v-card-title>
        <v-card-subtitle>Patient</v-card-subtitle>
        <PatientDetails :isEditable="true" :patientData="patientData"></PatientDetails>
    </v-card>
</template>

<script>
import PatientDetails from "@/components/Panels/DoctorPanel/MyPatients/PatientDetails";

export default {
    name: "PatientDetailsCard",
    components: {PatientDetails},
    props: {
        patientData: Object
    }
}
</script>

<style scoped>

</style>