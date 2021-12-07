<template>
<v-container fluid>
    <v-row justify="center">
    <v-col sm="8" xs="8" md="6" lg="4" align="center">
        <v-card>
            <v-card-subtitle class="display-1 text--primary">Verify your account</v-card-subtitle>
            <v-divider class="mx-4"></v-divider>
            <v-card-text>Your account has been created, but is inactive. Activation message has been sent to your e-mail: <b>{{email}}</b>
                <br>To activate your account, please visit your e-mail. In case you've not received an e-mail, please press the button below.</v-card-text>

                <v-row justify="center">
                    <v-card-actions>
                    <v-btn large rounded big @click="resendEmail" :loading="resendButtonLoading" color="orange">Resend confirmation e-mail</v-btn>
                    </v-card-actions>
                </v-row>
            <v-card-text>Once confirmed, your account will be available to use.</v-card-text>
        </v-card>
    </v-col>
    </v-row>
</v-container>
</template>

<script>
import axios from "@/axios";

export default {
    name: "MailConfirmation",
    data(){
      return{
          firstName: '',
          resendButtonLoading: false,
      }
    },
    props:{
        email: String,
    },
    methods: {
        resendEmail(){
            this.resendButtonLoading = true
            axios.post('registration/resend-email', {
                email: this.email,
                firstName: this.firstName
            }).then((response) => {
                console.log(response)
                this.$toast.success('Confirmation mail sent successfully!')
            }).finally(()=>{
                this.resendButtonLoading = false
            })
        }
    },
    created(){
        console.log(this.email)
        axios.get('users/first-name',{
            params: {
                email: this.email
            }
        }).then((response) => {
            this.firstName = response.data
        })
    }
}
</script>

<style scoped>

</style>