<template>
    <v-container fill-height fluid>
       <Success v-if="confirmationSucceeded"></Success>
        <MailAlreadyConfirmed v-if="emailAlreadyConfirmed"></MailAlreadyConfirmed>
        <TokenExpired :token="this.$route.params.token" v-if="tokenExpired"></TokenExpired>
    </v-container>
</template>

<script>

import axios from '@/axios'
import Success from "@/components/Registration/Confirmation/Success";
import MailAlreadyConfirmed from "@/components/Registration/Confirmation/MailAlreadyConfirmed";
import TokenExpired from "@/components/Registration/Confirmation/TokenExpired";
export default {
    name: "Confirmation",
    components: {TokenExpired, MailAlreadyConfirmed, Success},
    data(){
        return{
            tokenExpired: false,
            emailAlreadyConfirmed: false,
            confirmationSucceeded: false,
        }

    },
    created(){
        axios.get('registration/confirm?token=' + this.$route.params.token)
            .then(()=>{
                this.confirmationSucceeded = true
            }).catch((error) => {
            let errorMessage = error.response.data.message
            if(errorMessage === 'Token expired'){
                this.tokenExpired = true
                return
            }
            if(errorMessage === 'E-mail already confirmed'){
                this.emailAlreadyConfirmed = true
            }
        })
    }
}
</script>

<style scoped>

</style>