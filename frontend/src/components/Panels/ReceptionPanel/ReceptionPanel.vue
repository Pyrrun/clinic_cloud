<template>
    <div>
        <transition name="fade" mode="out-in">
            <v-app v-if="!messengerOpened && cardIndex !== 5" style="background-color: whitesmoke">
                <v-main>
                    <Navigation :navigationOpened="navigationOpened" @navigationClosed="navigationOpened = false" @navigationChosen="navigationChosen"></Navigation>
                    <v-img
                        gradient="to top, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.2)"
                        height="1000px"
                        :src="backgroundImage"
                    >

                        <v-toolbar style="background-color: transparent">
                            <v-app-bar-nav-icon  @click=openNavigationDrawer></v-app-bar-nav-icon>
                            <v-row justify="end" class="mr-4">
                                <v-btn @click="messengerOpened = true" class="mr-2" text color="white">Messenger</v-btn>
                                <!-- <v-btn text color="white" class="mr-2">My account</v-btn> -->
                                <v-btn color="error" class="mr-2" @click="logout">Sign out</v-btn>
                            </v-row>
                        </v-toolbar>
                        <v-container fill-height>
                            <v-container>
                                <v-row>
                                    <div class="text-uppercase  font-weight-bold mb-4 display-3">
                                        <vue-typer  erase-style='backspace' :text="text"></vue-typer>
                                    </div>
                                </v-row>
                                <v-row>
                                    <div class="mb-4  display-2 font-weight-light">Welcome to the reception panel
                                    </div>
                                </v-row>
                                <v-row>
                                    <div class="mb-4 display-1 font-weight-light font-italic">Enjoy your work</div>
                                </v-row>
                            </v-container>
                        </v-container>
                    </v-img>
                    <v-main class="mt-4">
                        <transition name="fade" mode="out-in">
                            <HomePage @cardChosen="cardChosen" v-if="cardIndex === 0"></HomePage>
                            <NewVisit v-if="cardIndex === 1" @returnClicked="cardIndex = 0"></NewVisit>
                            <PatientTable @newPatientClicked="cardIndex = 4" @returnClicked="cardIndex = 0" v-if="cardIndex === 2"></PatientTable>
                            <NewPatient v-if="cardIndex === 3" @registrationFinished="cardIndex = 0"></NewPatient>
                        </transition>
                    </v-main>
                </v-main>
                <Footer></Footer>
            </v-app>
            <Messenger @messengerClosed="messengerOpened = false; cardIndex = 0" v-else></Messenger>
        </transition>
    </div>
</template>

<script>
import Footer from "@/components/Footer";

import HomePage from "@/components/Panels/ReceptionPanel/HomePage/HomePage";
import NewVisit from "@/components/Panels/ReceptionPanel/NewVisit/NewVisit";
import Navigation from "@/components/Panels/ReceptionPanel/Navigation";
import Messenger from "@/components/Panels/Messenger/Messenger";
import { VueTyper } from 'vue-typer'
import PatientTable from "@/components/Panels/DoctorPanel/MyPatients/PatientTable";
import NewPatient from "@/components/Panels/ReceptionPanel/NewPatient/NewPatient";


export default {
    name: "ReceptionPanel",
    components: {HomePage, Footer, Navigation, NewVisit, Messenger, VueTyper, PatientTable, NewPatient},
    data() {
        return {
            backgroundImage: `${process.env.VUE_APP_BASE_URL}/images/static/reception-panel.jpg`,
            messengerOpened: false,
            isActive: false,
            patientData: '',
            cardIndex: 0,
            navigationOpened: false,
            selectedVisitDate: '',
            selectedVisitDuration: 0,
            text: 'Hello, ' + localStorage.getItem('firstName'),

        }
    },

    methods: {
        navigationChosen(index) {
            this.cardIndex = index
        },
        cardChosen(index) {
            this.cardIndex = index
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)
        },

        logout() {
            localStorage.removeItem('user')
            localStorage.removeItem('id')
            localStorage.removeItem('email')
            localStorage.removeItem('firstName')
            if (localStorage.getItem('imageName') != null) {
                localStorage.removeItem('imageName')
            }
            this.$store.state.webSocketConnectionEstablished = false
            this.$store.state.webSocket.close()
            this.$router.push({name: 'login'})

        },
        openNavigationDrawer() {
            this.navigationOpened = true
        },
    },

}

</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: all 1s;
}

.fade-enter, .fade-leave-to {
    opacity: 0
}
</style>