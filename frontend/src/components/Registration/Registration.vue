<template>
    <v-container fill-height>
    <transition name="fade" appear v-if="step === 0">
            <v-row justify="center">
                <v-col cols="11" lg="6">
                    <v-card elevation="10">
                        <v-card-title>
                            Registration
                        </v-card-title>
                        <v-card-subtitle>
                            Fill all the steps to register your account
                        </v-card-subtitle>
                        <transition v-if="isRegistrationTypeChosen" appear name="fade" mode="out-in">
                        <Steps :registrationType="registrationType" @registrationFinished="redirectToEmailConfirmation"></Steps>
                        </transition>
                        <transition v-else name="fade">
                        <RegistrationType  @registrationTypeChosen="goToStep1" ></RegistrationType>
                        </transition>
                    </v-card>
                </v-col>
            </v-row>
    </transition>
        <transition v-else name="fade" mode="out-in">
    <MailConfirmComponent  :email="userData.email"></MailConfirmComponent>
        </transition>
    </v-container>
</template>

<script>
import MailConfirmComponent from "@/components/Registration/Confirmation/MailConfirmation";
import Steps from "@/components/Registration/Steps";
import RegistrationType from "@/components/Registration/RegistrationType";

export default {
    data() {
        return {
            step: 0,
            isRegistrationTypeChosen: false,
            userData: {},
            registrationType: ''
        }
    },
    name: "RegisterComponent",
    components: {Steps, MailConfirmComponent, RegistrationType},
    methods: {
        redirectToEmailConfirmation(userData){
            this.userData = userData
            this.step = 5
        },
        goToStep1(registrationType){
            this.registrationType = registrationType
            this.isRegistrationTypeChosen = true
        }

    },


}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: opacity .5s ease;
}
</style>