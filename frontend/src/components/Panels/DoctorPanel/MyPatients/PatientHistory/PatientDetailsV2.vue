<template>
        <v-card-text>
            <v-list>
                <v-list-item>
                    <v-list-item-icon>
                        <v-icon color="indigo">
                            mdi-phone
                        </v-icon>
                    </v-list-item-icon>
                    <v-list-item-content>
                        <v-list-item-title>{{ patientData.phone }}</v-list-item-title>
                        <v-list-item-subtitle>Mobile</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
                <v-divider inset></v-divider>
                <v-list-item>
                    <v-list-item-icon>
                        <v-icon color="indigo">
                            mdi-calendar
                        </v-icon>
                    </v-list-item-icon>
                    <v-list-item-content>
                        <v-list-item-title>{{patientData.birthdate}}</v-list-item-title>
                        <v-list-item-subtitle>Date of birth</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
                <v-divider inset></v-divider>
                <v-list-item>
                    <v-list-item-icon>
                        <v-icon color="indigo">
                            mdi-email
                        </v-icon>
                    </v-list-item-icon>

                    <v-list-item-content>
                        <v-list-item-title>{{patientData.email}}</v-list-item-title>
                        <v-list-item-subtitle>Personal e-mail</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
                <v-divider inset></v-divider>
                <v-list-item>
                    <v-list-item-icon>
                        <v-icon color="indigo">
                            mdi-home
                        </v-icon>
                    </v-list-item-icon>
                    <v-list-item-content>
                        <v-list-item-title>{{ patientData.address.street }}/{{patientData.address.flatNumber}}<br><div class="font-weight-light">{{ patientData.address.postcode }} {{patientData.address.city}}</div></v-list-item-title>
                        <v-list-item-subtitle>Address</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
                <v-divider></v-divider>
                <v-list-item>
                    <v-list-item-icon>
                        <v-icon color="indigo">
                            mdi-identifier
                        </v-icon>
                    </v-list-item-icon>
                    <v-list-item-content>
                        <v-list-item-title>{{ patientData.pesel }}</v-list-item-title>
                        <v-list-item-subtitle>PESEL</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-card-text>


</template>

<script>
export default {
    name: "PatientDetailsV2",
    props:{
        patientData: Object
    },


}
</script>

<style scoped>

</style>