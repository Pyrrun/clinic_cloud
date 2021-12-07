<template>
    <div class="text-center">
        <h1 class="display-1 font-weight-thin mb-3">
            <b>Step 1</b>
        </h1>
        <h2 class="display-1 font-weight-thin mb-3">
            <i>Interview</i>
        </h2>
        <v-card-text>
            In order to provide the best possible treatment for your patient, try to examine him properly. Using our advice would be highly recommended.
        </v-card-text>
        <v-card-text>
            <v-carousel
                cycle
                v-model="model">
                <v-carousel-item
                    v-for="(tip, i) in tips"
                    :key="i"
                    :src="tip.src"

                >
                       <v-container fill-height>
                           <v-row justify="center">
                               <div class="display-3 font-weight-bold black--text text-center ">
                                   Tip {{ i + 1 }}<br>
                                   <div class="mt-1 display-1  font-weight-light font-italic text-center">{{tip.title}}</div>
                               </div>
                           </v-row>
                           <v-row justify="center">
                               <div class="font-weight-light black--text title ml-2 mr-2" >
                                   {{ tip.content }}
                               </div>
                           </v-row>

                       </v-container>

                </v-carousel-item>
            </v-carousel>
        </v-card-text>
        <v-card-text>
            <v-textarea
                v-model="interview"
                solo
                placeholder="Insert the information you have received from your patient"
            ></v-textarea>
        </v-card-text>
        <v-card-actions>
            <v-row justify="end">
            <v-btn text color="primary"  @click="goToStep2"
                >
                Save
            </v-btn>
            </v-row>
        </v-card-actions>

    </div>

</template>

<script>
export default {
    name: "Step1",
    data(){
        return{
            interview: '',
            model: 0,
            tips: [
                {
                    color: 'primary',
                    content: 'When did it start? Is this a new problem?',
                    title: 'History of Present Illness',
                    src: 'https://image.freepik.com/free-photo/doctor-s-stethoscope-with-blue-background_23-2147652363.jpg'
                },
                {
                    color: 'secondary',
                    title: 'Past Medical History',
                    content: 'Are you suffering from any illnesses? Have you had any surgeries or hospitalization? When was the last time you felt ill?',
                    src: 'https://image.freepik.com/free-photo/stethoscope-blue-background-top-view-space-text_185193-6316.jpg'
                },
                {
                    color: 'yellow darken-2',
                    title: 'Current medications',
                    content: 'Do you take any medicine? What is the dosage? Why are you taking your medicine? Have you had any side effects?',
                    src: 'https://image.freepik.com/free-photo/syringe-with-needle-blue-background-nurse-holds-injection-syringe_98785-352.jpg'
                },
                {
                    color: 'orange',
                    title: 'General',
                    content: 'Sleep, appetite, weight changes, fatigue, fever/chills/night sweats, do clothes fit differently?',
                    src: 'https://walrus-assets.s3.amazonaws.com/img/AdamsNeilson_NarrativeMedicine_735.jpg'
                },

            ],


        }
    },
    methods:{
        goToStep2(){
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)
            setTimeout(() => {
                this.$emit('firstStepComplete', this.interview)
            }, 200)

        }
    },

}
</script>

<style scoped>

</style>