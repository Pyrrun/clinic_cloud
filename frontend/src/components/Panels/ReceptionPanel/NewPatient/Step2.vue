<template>
  <div>
    <v-card-text>
      <v-row class="text-center mt-3">
        <v-col class="mb-4">
          <h2 class="display-1 mb-3">
            Choose a leading doctor for new patient
          </h2>
          <h3 class="headline" v-if="{ doctor }">
            {{
              doctor &&
              `${doctor.firstName} ${doctor.secondName} ${doctor.lastName}`
            }}
          </h3>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col lg="6" xs="10" sm="8">
          <v-responsive height="400px">
            <transition name="fade" appear mode="out-in">
              <v-img :key="imgKey" height="400px" :src="imgSrc"></v-img>
            </transition>
          </v-responsive>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col xs="10" md="6" sm="7" lg="6">
          <DoctorSearchComponent
            @doctorSelected="selectDoctor"
          ></DoctorSearchComponent>
        </v-col>
      </v-row>
      <v-row justify="center" class="mb-3">
        <v-col xs="10" md="6" sm="7" lg="6">
          <v-btn
            block
            large
            color="primary"
            :loading="isSubmitLoading"
            :disabled="!doctorSelected"
            @click="registerNewPatient"
            >Finish registration
          </v-btn>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions>
      <v-btn text color="primary" @click="returnToStep1">Return</v-btn>
    </v-card-actions>
  </div>
</template>

<script>
import DoctorSearchComponent from "@/components/Panels/ReceptionPanel/NewPatient/DoctorSearchComponent";
import { tokenAxios } from "@/axios";

export default {
  name: "Step2",
  components: { DoctorSearchComponent },
  props: {
    patient: Object,
  },
  data() {
    return {
      emptyImgSrc:
        "https://www.lupus.eu/en/assets/images/home/empty-avatar.png",
      doctorImgSrc: "",
      imgSrc: "https://www.lupus.eu/en/assets/images/home/empty-avatar.png",
      doctor: null,
      doctorSelected: false,
      imageChanged: false,
      imgKey: 0,
      isSubmitLoading: false,
    };
  },
  methods: {
    selectDoctor(doctor, doctorSelected) {
      this.doctor = doctor;
      this.doctorSelected = doctorSelected;
      if (!doctorSelected) return;
      const imgSrc = doctor.img || this.emptyImgSrc;
      imgSrc === this.imgSrc || this.imgKey++;
      this.imgSrc = imgSrc;
    },
    returnToStep1() {
      this.$emit("returnClicked");
    },
    registerNewPatient() {
      this.isSubmitLoading = true;
      console.log(this.patient)
      tokenAxios.post('patients', {
          doctorId: this.doctor.id,
          ...this.patient
      }).then((res) => {
          console.log(res.data)
          this.$toast.success("New patient has been successfully registered");
          this.$emit('registrationFinished')
      }).catch(err => {
          console.error(err)
          this.$toast.error('Oops, something went wrong')
      }).finally(() => {
          this.isSubmitLoading = false
      })
    },
  },
  watch: {
    imgSrc() {
      this.imageChanged = !this.imageChanged;
    },
  },
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>