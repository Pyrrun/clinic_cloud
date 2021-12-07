<template>

    <v-container fluid>
        <v-row justify="center">
            <v-col lg="3" xs="10" sm="8" md="6" align="center">
                <v-card>
                    <v-card-subtitle class="display-1 text--primary">Verification successful!</v-card-subtitle>
                    <v-divider class="mx-4"></v-divider>
                    <v-card-text>Your account has been activated. Now you can log in.<br> Thank you for using our services.</v-card-text>
                    <v-row justify="center">
                        <v-card-actions>
                        <v-col cols="6">
                            <v-btn rounded big color="primary" @click="moveToLogin">Sign in</v-btn>
                        </v-col>
                        </v-card-actions>
                    </v-row>

                </v-card>
            </v-col>
        </v-row>

</v-container>

</template>

<script>


export default {
    name: "Success",
    methods:{
        moveToLogin(){
            this.$router.push({name: 'login'})
        }
    },
}
</script>

<style scoped>

</style>