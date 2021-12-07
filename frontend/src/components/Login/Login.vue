<template>
    <v-container fill-height align-self="center" :style="background">
        <MailConfirmComponent v-if="!isAccountActive" :email="email"></MailConfirmComponent>
    <v-row v-else justify="end">
        <v-col lg="8" sm="8" md="8" align="center">
        <v-col lg="6"  xs="10" md="10" sm="10">
            <v-card elevation="10">
                <v-card-title class="display-2">
                    <v-spacer></v-spacer>
                    Welcome
                    <v-spacer></v-spacer>
                </v-card-title>
                <v-card-subtitle class="text-center">
                    Sign in to your workspace
                </v-card-subtitle>
                    <transition name="fade">
                        <p style="color:red;" class="text-center" v-if="errorPopped">{{ error }}</p>
                    </transition>
                <v-card-text class="text-center">
                    <v-row justify="center">
                        <v-col cols="10">
                            <v-form v-model="valid" class="login">
                                <v-text-field
                                    type="email"
                                    :rules="emailRules"
                                    clearable
                                    label="E-mail"
                                    error-count="1"
                                    required
                                    prepend-inner-icon="mdi-account-outline"
                                    v-model="email">

                                </v-text-field>
                                <v-text-field
                                    type="password"
                                    :rules="passwordRules"
                                    clearable
                                    label="Password"
                                    error-count="5"
                                    required
                                    prepend-inner-icon="mdi-lock-outline"
                                    v-model="password"
                                >
                                </v-text-field>
                            </v-form>

                        </v-col>
                    </v-row>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <transition name="fade">
                        <v-col cols="10">
                            <v-btn
                                color="primary"
                                class="mb-4"
                                @click="overlay=!overlay"
                                :disabled="!buttonEnabled"
                                block
                                large
                            >
                                Sign in
                            </v-btn>
                        </v-col>
                    </transition>
                    <v-spacer></v-spacer>
                </v-card-actions>
            </v-card>
        </v-col>
        </v-col>
    </v-row>
        <v-overlay :value="overlay">
            <v-progress-circular
                indeterminate
                size="64"
            ></v-progress-circular>
        </v-overlay>
    </v-container>
</template>

<script>
import axios from "@/axios";
import MailConfirmComponent from "@/components/Registration/Confirmation/MailConfirmation";
export default {
    name: "Login",
    components: {MailConfirmComponent},

    data(){
        return{
            background: `background-size: contain; background-image: url(${process.env.VUE_APP_BASE_URL}/images/static/logo_transparent.png)`,
            overlay: false,
            image: { backgroundImage : null},
            isAccountActive: true,
            email: '',
            password: '',
            buttonEnabled: false,
            errorPopped: false,
            valid: false,
            error: '',
            emailRules: [
                v => !!v || "Please enter your e-mail.",
                v => /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/.test(v) || 'Correct e-mail example: example@example.com'
            ],
            passwordRules: [
                v => !!v || "Please enter your password.",
                v => (v && v.length >= 8) || ("Your password should contain at least 8 digits."),
                v => /(?=.*[A-Z])/.test(v) || "Your password should contain at least one capital letter.",
                v => /(?=.*\d)/.test(v) || "Your password should contain at least one number.",
                v => /([!@$%])/.test(v) || "Your password should contain at least one special sign (!@$%).",


            ],

        }
    },


    methods : {
        login(){
            axios.post(`${process.env.VUE_APP_BASE_URL}/login`, {
                email: this.email,
                password: this.password
            }).then((response) => {
                localStorage.setItem('email', this.email)
                localStorage.setItem('user', response.data.token)
                localStorage.setItem('firstName', response.data.firstName)
                localStorage.setItem('secondName', response.data.secondName)
                localStorage.setItem('lastName', response.data.lastName)
                localStorage.setItem('id', response.data.id)
                localStorage.setItem('role', response.data.role)
                response.data.img && localStorage.setItem('img', response.data.img)
                response.data.imageName && localStorage.setItem('imageName', response.data.imageName)
                this.$router.push({name: 'panelSelector'})
            }).catch((error) => {
                this.errorPopped = true
                if(error.response.data.message === 'This user has not been activated'){
                    this.isAccountActive = false
                    return
                }
                this.error = error.response.data.message
            })
        },
        showButton() {
            this.buttonEnabled = this.valid;
        },
    },
    watch: {
        valid() {
            setTimeout(this.showButton, 200);
            this.$emit('validCheck', this.valid)
        },

            overlay (val) {

                val && setTimeout(() => {
                    this.overlay = false
                    this.login()
                }, 1000)
            },

    },
}
</script>

<style scoped>
.slide-enter-active,
.slide-leave-active{
    transition: all 1s;
}
.slide-enter, .slide-leave-to{
    transform: translateY(-100%);
}


</style>