<template>
  <div>
    <transition name="fade" mode="out-in">
      <v-app
        v-if="!messengerOpened && cardIndex !== 6"
        style="background-color: whitesmoke"
      >
        <v-main>
          <Navigation
            :navigationOpened="navigationOpened"
            @navigationClosed="navigationOpened = false"
            @navigationChosen="navigationChosen"
          ></Navigation>
          <v-img height="1000px" :src="backgroundImage">
            <v-toolbar style="background-color: transparent">
              <v-app-bar-nav-icon
                @click="openNavigationDrawer"
              ></v-app-bar-nav-icon>
              <v-row justify="end" class="mr-4">
                <!-- @click="messengerOpened = true" -->
                <v-btn class="mr-2" color="primary">Messenger</v-btn>
                <!-- <v-btn text  class="mr-2">My account</v-btn> -->
                <v-btn color="error" class="mr-2" @click="logout"
                  >Sign out</v-btn
                >
              </v-row>
            </v-toolbar>
            <v-container fill-height>
              <v-container>
                <v-row>
                  <div class="text-uppercase font-weight-bold mb-4 display-3">
                    <vue-typer erase-style="backspace" :text="text"></vue-typer>
                  </div>
                </v-row>
                <v-row>
                  <div class="mb-4 display-2 font-weight-light">
                    Welcome to your personal doctor panel
                  </div>
                </v-row>
                <v-row>
                  <div class="mb-4 display-1 font-weight-light font-italic">
                    Enjoy your work
                  </div>
                </v-row>
              </v-container>
            </v-container>
          </v-img>
          <v-main class="mt-4">
            <transition name="fade" mode="out-in">
              <HomePage
                @cardChosen="cardChosen"
                @visitStarted="beginVisit"
                v-if="cardIndex === 0"
              ></HomePage>
              <VisitCard
                @goBack="cardIndex = 0"
                v-if="cardIndex === 1"
              ></VisitCard>
              <MySchedule v-if="cardIndex === 2"></MySchedule>
              <PatientTable
                v-if="cardIndex === 3"
                @returnClicked="cardIndex = 0"
              ></PatientTable>
              <Visit
                :selectedVisitDate="selectedVisitDate"
                @visitFinished="cardIndex = 0"
                :selectedVisitDuration="selectedVisitDuration"
                :patientData="patientData"
                v-if="cardIndex === 5"
              >
              </Visit>
            </transition>
          </v-main>
        </v-main>
        <Footer></Footer>
      </v-app>
      <Messenger
        @messengerClosed="
          messengerOpened = false;
          cardIndex = 0;
        "
        v-else
      ></Messenger>
    </transition>
  </div>
</template>

<script>
import Navigation from "@/components/Panels/DoctorPanel/Navigation";
import VisitCard from "@/components/Panels/DoctorPanel/MyVisits/VisitCard";
import HomePage from "@/components/Panels/DoctorPanel/HomePage/HomePage";
import PatientTable from "@/components/Panels/DoctorPanel/MyPatients/PatientTable";
import MySchedule from "@/components/Panels/DoctorPanel/MySchedule/MySchedule";
import Visit from "@/components/Panels/DoctorPanel/Visit/Visit";
import Messenger from "@/components/Panels/Messenger/Messenger";
import Footer from "@/components/Footer";
import { VueTyper } from "vue-typer";

export default {
  name: "DoctorPanel",
  components: {
    Footer,
    Messenger,
    PatientTable,
    MySchedule,
    HomePage,
    VisitCard,
    Navigation,
    Visit,
    VueTyper,
  },
  data() {
    return {
      backgroundImage: `${process.env.VUE_APP_BASE_URL}/images/static/doctor-panel1.jpg`,
      messengerOpened: false,
      isActive: false,
      patientData: "",
      cardIndex: 0,
      navigationOpened: false,
      selectedVisitDate: "",
      selectedVisitDuration: 0,
      text: "Hello, " + localStorage.getItem("firstName"),
    };
  },

  methods: {
    navigationChosen(index) {
      this.cardIndex = index;
    },
    cardChosen(index) {
      this.cardIndex = index;
      this.$vuetify.goTo(this.$store.state.target, this.$store.state.options);
    },

    logout() {
      localStorage.removeItem("user");
      localStorage.removeItem("id");
      localStorage.removeItem("email");
      localStorage.removeItem("firstName");
      localStorage.removeItem("secondName");
      localStorage.removeItem("lastName");
      localStorage.removeItem("role");
      localStorage.removeItem("img");
      this.$router.push({ name: "login" });
      this.$store.state.webSocketConnectionEstablished = false;
      this.$store.state.webSocket.close();
    },
    openNavigationDrawer() {
      this.navigationOpened = true;
    },
    beginVisit(patientData, selectedVisitDate, selectedVisitDuration) {
      this.cardIndex = 5;
      this.patientData = patientData;
      this.selectedVisitDate = selectedVisitDate;
      this.selectedVisitDuration = selectedVisitDuration;
    },
  },
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: all 1s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>