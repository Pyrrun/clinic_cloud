<template>
    <div>
<v-card-text class="text-center">
    <span class="display-1">Welcome to the registration panel</span><br>
    <span class="">What is your profession?</span><br>
    <v-divider class="mt-2"></v-divider>
</v-card-text>
    <v-card-actions>
        <v-row justify="center">
        <v-col cols="10" lg="6"  align="center">
            <v-hover v-slot="{hover}">
                <v-card
                    @click="chooseDoctorType"
                    :elevation="(hover||doctorTypeChosen)?7:1"
                >
                    <v-card-text>
                <v-img height="300"  width="300" src="https://www.patientpop.com/wp-content/uploads/national-doctors-day-1.jpg"> </v-img>
                    <v-expand-transition>
                        <div v-if="hover || doctorTypeChosen" class="d-flex transition-fast-in-fast-out transparent align-center darken-2 v-card--reveal display-3 black--text " style="height: 100%;">
                            I'm a doctor
                        </div>
                    </v-expand-transition>

                    </v-card-text>
                </v-card>
            </v-hover>
        </v-col>

        <v-col cols="10" lg="6" align="center">
            <v-hover v-slot="{hover}">
                <v-card
                    :elevation="(hover || receptionistTypeChosen)?7:1"
                    @click="chooseReceptionistType(hover)"
                >
                    <v-card-text>
            <v-img height="300" width="300" src="https://jobtoday.com/wp-content/uploads/2018/05/receptionist.jpg"></v-img>
                        <v-expand-transition>
                            <div v-if="hover || receptionistTypeChosen" class="d-flex transition-fast-in-fast-out transparent align-self-auto darken-2 v-card--reveal display-3 black--text " style="height: 100%;">
                                I'm a reception worker
                            </div>
                        </v-expand-transition>
                    </v-card-text>
                </v-card>
            </v-hover>
        </v-col>

        </v-row>
    </v-card-actions>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :disabled="!isTypeChosen" @click="chooseRegistrationType" text large color="primary">Continue</v-btn>
        </v-card-actions>
    </div>
</template>

<script>
export default {
    name: "RegistrationType",
    data(){
        return{
            receptionistTypeChosen: false,
            doctorTypeChosen: false
        }
    },

    computed: {
        isTypeChosen(){
            return this.receptionistTypeChosen || this.doctorTypeChosen;
        },
        chosenType(){
            if(this.receptionistTypeChosen){
                return 'receptionistType'
            }
            if(this.doctorTypeChosen){
                return 'doctorType'
            }
            return ''

        }
    },

    methods: {
        chooseDoctorType(){
            if(!this.doctorTypeChosen){
                this.receptionistTypeChosen = false
            }
            this.doctorTypeChosen = !this.doctorTypeChosen
        },
        chooseReceptionistType(){
            if(!this.receptionistTypeChosen){
                this.doctorTypeChosen = false
            }
            this.receptionistTypeChosen = !this.receptionistTypeChosen
        },
        chooseRegistrationType(){
            this.$emit('registrationTypeChosen', this.chosenType)
        }
    }


}
</script>

<style scoped>
.v-card--reveal {
    align-items: center;
    bottom: 0;
    justify-content: center;
    opacity: .5;
    position: absolute;
    width: 100%;
}

</style>