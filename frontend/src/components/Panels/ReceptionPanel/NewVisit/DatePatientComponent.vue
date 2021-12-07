<template>
        <v-container class="mt-5">
            <v-row justify="center">
                <h2 class="display-1 font-weight-thin mb-3">
                    Please choose a visit date
                </h2>
            </v-row>
            <v-row justify="center">
                <DatePicker @dateChanged="getDate" class="mt-8"/>
            </v-row>
            <v-row justify="center" class="mt-4">
                <v-col cols="7">
                    <PatientSearchComponent @patientSelected="getPatient"></PatientSearchComponent>
                </v-col>
            </v-row>
        </v-container>
</template>

<script>
import DatePicker from "@/components/Panels/ReceptionPanel/NewVisit/DatePicker";
import PatientSearchComponent from "@/components/Panels/ReceptionPanel/NewVisit/PatientSearchComponent";
import {tokenAxios} from "@/axios";


export default {
    components: {DatePicker, PatientSearchComponent},
    data() {
        return {
            doctors: [],
            // date: '',
            // patientSelected: false,
            // patient: null,
            // loading: false
        }
    },

    methods: {

        getPatient(patient, patientSelected) {
            this.patient = patient
            this.patientSelected = patientSelected
            this.$emit('patientSelected', patient, patientSelected)
        },
        getDate(date) {
            this.date = date
            this.$emit('dateChosen', date)
        },

        getDoctors() {
            this.loading = true
            tokenAxios.get('doctors', {
                params: {
                    date: this.date
                }
            })
                .then(response => {
                    this.doctors = response.data
                    this.$emit('patientAndDateSelected', this.patient, this.date, this.doctors)
                }).catch(err => {
                    console.log(err)
                    this.$toast.error('Oops, something went wrong')
                })
                .finally(() => this.loading = false)
        }
    },
}
</script>