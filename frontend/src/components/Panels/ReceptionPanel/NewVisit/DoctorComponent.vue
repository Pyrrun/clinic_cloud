<template>
  <v-card height="790" class="flex-card">
    <v-card-title>Available doctors</v-card-title>
    <v-card-subtitle
      >Below you can find a list of available doctors on chosen
      date</v-card-subtitle
    >
    <v-card-text class="flex-card-text">
      <v-divider></v-divider>
      <v-row >
        <v-col cols="3">
          <v-navigation-drawer  permanent v-model="drawer">
            <v-list rounded>
              <v-subheader>DOCTORS</v-subheader>
              <v-list-item-group color="primary" v-model="selectedItem">
                <v-list-item
                  v-for="(doctor, i) in doctors"
                  :key="i"
                  @click="openDoctorCard(doctor)"
                >
                  <v-list-item-content>
                    <v-list-item-title
                      v-text="'dr ' + doctor.firstName + ' ' + doctor.lastName"
                    >
                    </v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-navigation-drawer>
        </v-col>
        <v-col cols="9" text-align="center">
          <transition name="fade" appear mode="out-in">
            <DoctorDetails
              v-if="selectedItem !== undefined"
              :key="selectedItem"
              :date="date"
              :doctor="doctor"
              :patient="patient"
            >
            </DoctorDetails>
          </transition>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions> 
        <v-btn @click="goBack" text color="primary">Return</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import DoctorDetails from "@/components/Panels/ReceptionPanel/NewVisit/DoctorDetails";

export default {
  components: { DoctorDetails },
  props: {
    patient: Object,
    date: String,
    doctors: Array,
  },
  data() {
    return {
      doctor: null,
      selectedItem: undefined,
      drawer: true,
      selectedItemChanged: false,
    };
  },

  watch: {
    selectedItem() {
      this.$forceUpdate();
    },
  },

  methods: {
    openDoctorCard(doctor) {
      console.log(doctor);
      this.doctor = doctor;
    },

    goBack() {
      this.$emit("returnClicked");
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

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s;
}
</style>