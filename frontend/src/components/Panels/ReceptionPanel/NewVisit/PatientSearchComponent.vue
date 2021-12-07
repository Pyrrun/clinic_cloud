<template>
    <v-autocomplete
        placeholder="Fill in patient data"
        prepend-icon="mdi-database-search"
        :search-input.sync="search"
        :loading="loading"
        :items="items"
        label="Patients"
    >
    </v-autocomplete>
</template>

<script>
import {tokenAxios} from '@/axios'

export default {
    data() {
        return {
            loading: false,
            search: null,
            items: [],
            patient: null,
            patientSelected: false
        }
    },
    watch: {
        search(val) {
            this.patientSelected = false
            this.loading = true
            this.findPatientsDebounced(val)
        },
    },
    methods: {
        findPatientsDebounced(val) {
            clearTimeout(this._searchTimerId)
            this._searchTimerId = setTimeout(() => {
                this.findPatients(val)
            }, 300)

        },

        findPatients(val) {
            if (!val) {
                this.items = []
                this.loading = false
                this.patientSelected = false
                this.$emit('patientSelected', this.patient, this.patientSelected)
                return
            }
            tokenAxios.get("patients", {
                params: {
                    name: val
                }
            }).then((response) => {
                if(this.items.includes(val)){
                    this.patientSelected = true
                    return
                }
                if(response.data.length === 0){
                    this.patientSelected = false
                }
                response.data.filter((patient) => {
                    this.patient = patient
                    const patientData = patient.firstName + " " + patient.lastName + " " + patient.pesel;
                    if (!this.items.includes(patientData)) {
                        this.items.push(patientData)
                    }
                })
            }).finally(() => {
                this.loading = false
                this.$emit('patientSelected', this.patient, this.patientSelected)
            })

        }

    }
}
</script>

<style>

</style>