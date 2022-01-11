<template>
    <v-card-text>
        <v-row class="text-center">
            <v-col class="mb-4">
                <h2 class="display-1 font-weight-bold mb-3">
                    Step 4
                </h2>
                <h3 class="headline mb-3">
                    <i>Specialization selection</i>
                </h3>
                <h4>Please select your specializations from options mentioned below </h4>
            </v-col>
        </v-row>
        <v-row justify="center">
            <v-col lg="7" xs="12" sm="10" md="7">
                <!-- <v-card>
                    <v-card-title>Please choose your specialization</v-card-title>
                    <v-card-text> -->
                        <v-select
                            placeholder="Please choose your specialization"
                            prepend-icon="mdi-database-search"
                            multiple
                            chips
                            :loading="loading"
                            :items="specializations"
                            v-model="selectedSpecializations"
                            label="Specializations"
                            cache-items
                        >
                        </v-select>
                    <!-- </v-card-text>

                </v-card> -->
                <v-col cols="12">
                    <v-row justify="center" class="mt-3">
                        <v-btn
                            color="primary"
                            :loading="registerLoading"
                            @click="registerDoctor"
                            block
                            :disabled="selectedSpecializations.length === 0"
                        >Submit
                        </v-btn>
                    </v-row>
                </v-col>
            </v-col>
        </v-row>
    </v-card-text>
</template>

<script>
import axios from '@/axios'

export default {
    name: "Step4",
    props: {
        userData: Object,
        image: File,
    },
    data() {
        return {
            specializations: [],
            loading: false,
            registerLoading: false,
            search: '',
            selectedSpecializations: [],
            fetchedSpecializations: []
        }
    },
    created() {
        console.log(this.userData)
        axios.get(`${process.env.VUE_APP_AUTH_URL}/auth/specializations`).then((response) => {
            console.log('response.data', response.data)
            this.fetchedSpecializations = response.data.data.specializations;
            response.data.data.specializations.forEach((element) => {
                this.specializations.push(element.name)
            })
        })
    },

    methods: {


        registerDoctor() {
            
            const specializationsId = this.selectedSpecializations.map(spec => this.fetchedSpecializations.find(item => item.name === spec).id)
            this.registerLoading = true
            console.log('thiasdasds', this.userData)
            this.userData['registrationType'] = 'doctorType'
            console.log('this.userData', {...this.userData, specializationIds:specializationsId})
            axios.post(`${process.env.VUE_APP_AUTH_URL}/auth/registration`, {...this.userData, specializationIds:specializationsId})
                .then(() => {
                    this.$emit('fourthStepComplete')
                    this.$toast.success('Congratulations! You have registered successfully.')
                })
                .catch((error) => {
                        this.$toast.error('Registration failed. Some of the fields are invalid.')
                        console.log(error)
                    }
                ).finally(() => {
                this.registerLoading = false
            })

        }
    },
}
</script>

<style scoped>

</style>