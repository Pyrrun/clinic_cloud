<template>
            <v-card-text>
                <v-list>
                    <v-item-group v-for="(item, i) in items" :key=i>
                        <v-list-item>
                            <v-list-item-icon>
                                <v-icon color="primary">
                                    {{item.icon}}
                                </v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <v-list-item-title>{{ item.title}}</v-list-item-title>
                                <v-list-item-subtitle>{{ item.subtitle }}</v-list-item-subtitle>
                            </v-list-item-content>
                            <v-list-item-icon v-if="isEditable">
                                <v-icon color="indigo" @click="edit(item)">
                                    mdi-pencil
                                </v-icon>
                            </v-list-item-icon>
                        </v-list-item>
                        <v-divider inset></v-divider>
                    </v-item-group>
                    <v-list-item>
                        <v-list-item-icon>
                            <v-icon color="primary">
                                mdi-home
                            </v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                            <v-list-item-title>{{ patientData.address.street }}{{patientData.address.flatNumber &&  `/`}}{{patientData.address.flatNumber}}<br><div class="font-weight-light">{{ patientData.address.postcode }} {{patientData.address.city}}</div></v-list-item-title>
                            <v-list-item-subtitle>Address</v-list-item-subtitle>
                        </v-list-item-content>
                        <v-list-item-icon>
                            <v-icon color="indigo" v-if="isEditable">
                                mdi-pencil
                            </v-icon>
                        </v-list-item-icon>
                    </v-list-item>
                </v-list>
            </v-card-text>
</template>

<script>
export default {
    name: "PatientDetails",
    props:{
        patientData: Object,
        isEditable: Boolean
    },
    data(){
        return{
            dialog: true,
            items: [
                {title: this.patientData.phone , subtitle: 'Phone number', icon: 'mdi-phone'},
                {title: this.patientData.birthdate, subtitle: 'Date of birth', icon: 'mdi-calendar'},
                {title: this.patientData.email, subtitle: 'E-mail', icon: 'mdi-email'},
                {title: this.patientData.pesel, subtitle: 'PESEL', icon: 'mdi-identifier'},
            ]
        }
    },
    methods: {
        edit(item){
            console.log(item)
        }
    }

}
</script>

<style scoped>

</style>