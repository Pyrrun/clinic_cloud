<template>
    <div class="text-center">
        <h1 class="display-1 font-weight-thin mb-3">
            <b>Step 2</b>
        </h1>
        <h2 class="display-1 font-weight-thin mb-3">
            <i>Research</i>
        </h2>
        <v-card-text>
            In this step you can store the information you have gathered from examinations on your patient.
        </v-card-text>
        <v-card-text>
            <v-container fluid>
                <v-row dense>
                    <v-col
                        v-for="card in cards"
                        :key="card.title"
                        :cols="card.flex"
                    >
                        <v-hover
                            v-slot="{hover}">
                            <v-card
                                :elevation="hover ? 16 : 2"
                                @click="redirect(card.index)"
                            >

                                <v-card-title v-text="card.title"></v-card-title>
                                <v-img
                                    :src="card.src"
                                    class="white--text align-end"
                                    gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                    height="200px"
                                    
                                >

                                </v-img>
                            </v-card>

                        </v-hover>
                    </v-col>
                </v-row>
            </v-container>
        </v-card-text>
        <v-card-text>
            <v-textarea
                v-model="research"
                solo
                placeholder=" What kind of examination have you provided your patient with? What are the results?"
            ></v-textarea>
        </v-card-text>
        <v-card-actions>
            <v-row justify="space-between">

                <v-btn text color="primary" @click="goBack">
                    Return
                </v-btn>
                <v-btn text color="primary" @click="goToStep3">
                    Save
                </v-btn>
            </v-row>
        </v-card-actions>
        <v-dialog max-width="500px" v-model="bloodPressureTest">
            <BloodPressureTest @bloodPressureTestSaved="saveBloodPressure"></BloodPressureTest>
        </v-dialog>
        <v-dialog max-width="500px" v-model="auscultation">
            <Auscultation @ausculationSaved="saveAusculation"></Auscultation>
        </v-dialog>
        <v-dialog max-width="500px" v-model="weightAndHeight">
            <WeightAndHeight @weightAndHeightSaved="saveWeightAndHeight"></WeightAndHeight>
        </v-dialog>
        <v-dialog max-width="500px" v-model="pulseCheck">
            <PulseCheck @pulseSaved="savePulse"></PulseCheck>
        </v-dialog>
    </div>

</template>

<script>
import BloodPressureTest from "@/components/Panels/DoctorPanel/Visit/BloodPressureTest";
import Auscultation from "@/components/Panels/DoctorPanel/Visit/Auscultation";
import WeightAndHeight from "@/components/Panels/DoctorPanel/Visit/WeightAndHeight";
import PulseCheck from "@/components/Panels/DoctorPanel/Visit/PulseCheck";

export default {
    name: "Step2",
    components: {PulseCheck, WeightAndHeight, Auscultation, BloodPressureTest},
    data() {
        return {
            auscultation: false,
            weightAndHeight: false,
            bloodPressureTest: false,
            pulseCheck: false,
            model: 0,
            research: '',
            cards: [
                {
                    title: 'Blood pressure test',
                    src: 'https://post.healthline.com/wp-content/uploads/2020/04/high-blood-pressure-hypertension-symptoms_thumb-732x549.jpg',
                    flex: 6,
                    index: 0
                },
                {
                    title: 'Auscultation',
                    src: 'https://post.healthline.com/wp-content/uploads/2020/08/Auscultation-732x549-thumbnail-1-732x549.jpg',
                    flex: 6,
                    index: 1
                },
                {
                    title: 'Weight and height measurement',
                    src: 'https://uppergrandfht.org/wp-content/uploads/2019/01/scale-lose-weight-2-1080x675.jpg',
                    flex: 6,
                    index: 2
                },
                {title: 'Pulse check', src: 'https://image.freepik.com/free-photo/doctor-checking-pulse-by-hand_38810-7841.jpg', flex: 6, index: 3},
            ],

        }
    },
    methods: {
        goBack() {
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)
            setTimeout(() => {
                this.$emit('goBack')
            }, 200)

        },
        goToStep3() {
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)
            setTimeout(() => {
                this.$emit('secondStepComplete', this.research)
            }, 200)


        },
        redirect(cardIndex) {
            if (cardIndex === 0) {
                this.bloodPressureTest = true
            }
            if (cardIndex === 1) {
                this.auscultation = true
            }
            if (cardIndex === 2) {
                this.weightAndHeight = true
            }
            if(cardIndex === 3){
                this.pulseCheck = true
            }
        },

        savePulse(text){
          this.research += text
          this.pulseCheck = false
        },
        saveBloodPressure(text){
            this.research += text
            this.bloodPressureTest = false
        },
        saveWeightAndHeight(text){
            this.research += text
            this.weightAndHeight = false
        },
        saveAusculation(text){
            this.research += text
            this.auscultation = false
        }
    }
}
</script>

<style scoped>

</style>