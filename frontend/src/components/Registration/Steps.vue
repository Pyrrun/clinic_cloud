<template>
    <div>
        <v-card-text>
            <v-stepper
                non-linear
                v-model="step"
                alt-labels
                class="elevation-0"
            >
                <v-stepper-header>
                    <v-stepper-step
                        step="1"
                        :complete="step > 1"
                    >
                        Login credentials
                    </v-stepper-step>
                    <v-divider></v-divider>
                    <v-stepper-step step="2" :complete="step > 2"
                    >
                        Personal data
                    </v-stepper-step>
                    <v-divider></v-divider>
                    <v-stepper-step step="3" :complete="step > 3">
                        Profile image
                        <small>Optional</small>
                    </v-stepper-step>
                    <v-divider v-if="registrationType === 'doctorType'"></v-divider>
                    <v-stepper-step step="4" v-if="registrationType === 'doctorType'" :complete="step > 4"
                    >
                        Specialization
                    </v-stepper-step>
                </v-stepper-header>
                <v-stepper-items>
                    <v-stepper-content step="1">
                        <Step1
                            @firstStepComplete="firstStepComplete">
                        </Step1>
                    </v-stepper-content>
                    <v-stepper-content step="2">
                        <Step2
                            @secondStepComplete="secondStepComplete"
                        ></Step2>
                    </v-stepper-content>
                    <v-stepper-content step="3">
                        <Step3
                            @thirdStepComplete="thirdStepComplete"
                            :continueClicked="continueClicked"
                            :registrationType="registrationType"
                        >
                        </Step3>
                    </v-stepper-content>
                    <v-stepper-content step="4" v-if="registrationType === 'doctorType'">
                        <Step4
                            :userData="userData"
                            @fourthStepComplete="fourthStepComplete"
                            :image="image"
                        >
                        </Step4>
                    </v-stepper-content>
                </v-stepper-items>
            </v-stepper>
        </v-card-text>
        <v-card-actions>
            <v-btn color="primary" v-show="step > 1" text @click="goBack">
                Return
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary" v-show="step === 3 && registrationType === 'doctorType'" @click="nextStep" text>
                Continue
            </v-btn>
            <v-btn color="primary"
                   v-show="step === 3 && registrationType === 'receptionistType'"
                   @click="nextStep"
                   text
                   :loading="registerLoading"
            >Submit
            </v-btn>
        </v-card-actions>
    </div>
</template>

<script>
import Step2 from "@/components/Registration/Step2";
import Step1 from "@/components/Registration/Step1";
import Step3 from "@/components/Registration/Step3";
import Step4 from "@/components/Registration/Step4";
import axios from "@/axios";

export default {
    name: "Steps",
    components: {Step2, Step1, Step3, Step4},
    props: {
        registrationType: String
    },
    data() {
        return {
            registerLoading: false,
            step: 1,
            valid: true,
            userData: {},
            image: null,
            continueClicked: false
        }
    },

    methods: {
        registerReceptionist() {
            this.registerLoading = true
            let registrationType = {registrationType : 'receptionistType'}
            this.userData = Object.assign(this.userData, registrationType)
            console.log(this.userData)
            const formData = new FormData()
            for (const element in this.userData) {
                formData.append(element.toString(), this.userData[element])
            }
            axios.post("registration", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
            })
                .then(() => {
                    this.$emit('registrationFinished', this.userData)
                    this.$toast.success('Congratulations! You have registered successfully.')
                })
                .catch((error) => {
                        this.$toast.error('Registration failed. Some of the fields are invalid.')
                        console.log(error)
                    }
                ).finally(() => {
                this.registerLoading = false
            })
        },


        firstStepComplete(form) {
            this.step = 2
            this.userData = Object.assign(this.userData, form)
        },

        secondStepComplete(form) {
            this.step = 3
            this.userData = Object.assign(this.userData, form)
        },
        thirdStepComplete(img) {
            if (img) {
                this.userData['img'] = img
            }
            if (this.registrationType === 'doctorType') {
                this.step = 4
                return
            }
            this.registerReceptionist()
        },
        fourthStepComplete() {
            this.$emit('registrationFinished', this.userData)
        },

        goBack() {
            this.step--
        },

        nextStep() {
            this.continueClicked = !this.continueClicked
        }


    }

}
</script>

<style scoped>

</style>