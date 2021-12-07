<template>
    <v-container fluid>
        <v-row justify="center" class="mb-2">
            <v-col xs="11" md="10" lg="5">
                <v-card>
                    <v-card-title>
                        Patient registration
                    </v-card-title>
                    <v-card-subtitle>
                        In this card you can register new person as patient in your clinic
                    </v-card-subtitle>
                    <transition name="slide" mode="out-in">
                        <Step1 @firstStepFinished="goToStep2"  v-if="step === 1"></Step1>
                        <Step2 @returnClicked="step = 1" :patient="patient" @registrationFinished="finishRegistration"  v-else></Step2>
                    </transition>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
//import {tokenAxios} from "@/axios";

import Step2 from "@/components/Panels/ReceptionPanel/NewPatient/Step2";
import Step1 from "@/components/Panels/ReceptionPanel/NewPatient/Step1";

export default {
    components: {Step1, Step2},
    data(){
        return{
            step: 1,
            patient: null
        }
    },
    methods: {
        goToStep2(patient){
            this.step = 2
            this.patient = patient
        },
       finishRegistration(){
            this.$emit('registrationFinished')
        }

    }
}
</script>

<style scoped>
.slide-enter-active,
.slide-leave-active{
    transition: all 1s;
}

.slide-enter, .slide-leave-to{
    opacity: 0
}
</style>