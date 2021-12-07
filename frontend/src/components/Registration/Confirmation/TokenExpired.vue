<template>
    <v-container fluid>
        <v-row justify="center">
            <v-col cols="4" align="center">
                <v-card>
                    <v-card-subtitle class="display-1 text--primary">Your token has expired!</v-card-subtitle>
                    <v-divider class="mx-4"></v-divider>
                    <v-card-text>Activation of your account has failed, because your confirmation token has expired. To activate your account, please press the button below. </v-card-text>
                    <v-row justify="center">
                        <v-card-actions>
                            <v-col cols="6">
                                <v-btn rounded big @click="refresh" color="primary">RESEND CONFIRMATION E-MAIL</v-btn>
                            </v-col>

                        </v-card-actions>
                        <v-card-text>Confirmation message will be sent to your e-mail address. </v-card-text>

                    </v-row>
                    <v-card-actions>
                        <v-btn text color="primary" @click="goBack">RETURN</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import axios from "@/axios";

export default {
    name: "TokenExpired",
    props : {
        token: String
    },
    methods: {
        goBack(){
            this.$router.push({name:'login'})
        },
        refresh(){
            axios.post('registration/resend-token', {
                token: this.token
            })
                .then((response) => {
                console.log(response)
            })
        }
    }
}
</script>

<style scoped>

</style>