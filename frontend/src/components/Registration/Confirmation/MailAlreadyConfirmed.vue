<template>
    <v-container fluid>
        <v-row justify="center">
            <v-col cols="3" align="center">
                <v-card>
                    <v-card-subtitle class="display-1 text--primary">Account is active</v-card-subtitle>
                    <v-divider class="mx-4"></v-divider>
                    <v-card-text>Your account has already been activated. Feel free to sign in. </v-card-text>
                    <v-card-actions>
                        <v-col>
                            <v-row justify="center">
                                <v-btn
                                    color="primary"
                                    @click="redirect"
                                >Sign in
                                </v-btn>

                            </v-row>
                            <v-row>
                                <v-card-text>
                                    Thank you for using our services.
                                </v-card-text>

                            </v-row>
                            <v-row>
                                <v-btn
                                    color="primary"
                                    text
                                    @click="goBack"
                                >

                                    Return
                                </v-btn>
                            </v-row>
                        </v-col>

                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: "MailAlreadyConfirmed",
    methods:{
        goBack(){
            this.$router.push({name : 'home'})
        },
        redirect(){
            this.$router.push({name : 'login'})
        }
    }
}
</script>

<style scoped>

</style>