<template>
    <v-autocomplete
        placeholder="Fill in patient data"
        prepend-icon="mdi-database-search"
        :search-input.sync="search"
        :loading="loading"
        :items="items"
        label="Doctors"
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
            doctor: null,
            doctorSelected: false,
        }
    },
    watch: {
        search(val) {
            this.doctorSelected = false
            this.loading = true
            this.findDoctorsDebounced(val)
        },
    },
    methods: {
        findDoctorsDebounced(val) {
            clearTimeout(this._searchTimerId)
            this._searchTimerId = setTimeout(() => {
                this.findDoctors(val)
            }, 300)

        },

        findDoctors(val) {
            if (!val) {
                this.items = []
                this.loading = false
                this.doctorSelected = false
                this.$emit('doctorSelected', this.doctor, this.doctorSelected)
                return
            }
            tokenAxios.get("doctors", {
                params: {
                    name: val
                }
            }).then((response) => {
                if(this.items.includes(val)){
                    this.doctorSelected = true
                    this.$emit('doctorSelected', this.doctor, this.doctorSelected)
                    return
                }
                 if(response.data.length === 0){
                     this.doctorSelected = false
                     this.$emit('doctorSelected', this.doctor, this.doctorSelected)
                     return
                 }
                response.data.filter((doctor) => {
                    this.doctor = doctor
                    const doctorData = doctor.firstName + " " + doctor.lastName;
                    if (!this.items.includes(doctorData)) {
                        this.items.push(doctorData)
                    }
                })
            }).finally(() => {
                this.loading = false
            })

        }

    }
}
</script>

<style>

</style>