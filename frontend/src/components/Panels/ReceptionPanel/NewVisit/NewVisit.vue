<template>
  <v-container fluid fill-height>
    <v-row justify="center" class="mb-3 mt-3">
      <v-col  lg="6" xs="12" sm="10" md="8" v-if="step === 1">
        <transition name="fade" appear mode="out-in">
          <DoctorComponent
            @returnClicked="step = 0"
            :key="date"
            :patient="patient"
            :doctors="doctors"
            :date="date"
          ></DoctorComponent>
        </transition>
      </v-col>
      <v-col lg="4" xs="12" sm="10" md="8">
        <v-card height="100%" class="flex-card">
          <v-card-title> New visit </v-card-title>
          <v-card-subtitle
            >In this card you can reserve a visit for your
            patient
            </v-card-subtitle>
          <v-card-text class="flex-card-text">
            <v-divider></v-divider>
            <v-container height="100%" class="mt-5">
              <v-row justify="center">
                <h2 class="display-1 font-weight-thin mb-3 text-center">
                  Please choose a visit date
                </h2>
              </v-row>
              <v-row justify="center">
                <DatePicker @dateChanged="getDate" class="mt-8" />
              </v-row>
              <v-row justify="center" class="mt-4">
                <v-col lg="7" xs="10" sm="8" md="7">
                  <PatientSearchComponent
                    @patientSelected="getPatient"
                  ></PatientSearchComponent>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
              <v-btn v-if="step === 0" text @click="goBack" large color="primary" >Return</v-btn>
            <v-spacer />
            <v-btn
              @click="getDoctors"
              :loading="loading"
              text
              large
              :disabled="!date || !patientSelected"
              color="primary"
              >Submit</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import DoctorComponent from "@/components/Panels/ReceptionPanel/NewVisit/DoctorComponent";
import DatePicker from "@/components/Panels/ReceptionPanel/NewVisit/DatePicker";
import PatientSearchComponent from "@/components/Panels/ReceptionPanel/NewVisit/PatientSearchComponent";
import { tokenAxios } from "@/axios";

export default {
  name: "NewVisit",
  components: { DoctorComponent, PatientSearchComponent, DatePicker },
  data() {
    return {
      loading: false,
      patientSelected: false,
      step: 0,
      patient: null,
      date: "",
      doctors: [],
      animation: "",
      cols: 5,
    };
  },
  methods: {
    getPatient(patient, patientSelected) {
      this.patient = patient;
      this.patientSelected = patientSelected;
    },
    goBack() {
      this.$emit("returnClicked");
    },
    getDate(date) {
      this.date = date;
    },
    getDoctors() {
      this.loading = true;
      tokenAxios
        .get("doctors", {
          params: {
            date: this.date,
          },
        })
        .then((response) => {
          this.doctors = response.data;
          this.step = 1;
          this.cols = 4;
        })
        .catch((err) => {
          console.log(err);
          this.$toast.error("Oops, something went wrong");
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
};
</script>

<style scoped>
.flex-card{
  display: flex;
  flex-direction: column;
}

.flex-card-text{
  flex-grow: 1
}
@keyframes animate-transform {
  50% {
    opacity: 0;
  }
  100% {
    opacity: 100;
  }
}

.fade-leave-active,
.fade-enter-active {
  transition: all 0.5s;
}

.fade.enter,
.fade-leave-to {
  opacity: 0;
}

.transform {
  animation-name: animate-transform;
  animation-fill-mode: forwards;
  animation-duration: 2s;
}

.test {
  transform: translateX(800px);
  transition: all 1s;
}
</style>