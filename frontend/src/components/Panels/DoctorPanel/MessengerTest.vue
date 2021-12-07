<template>
    <v-container fill-height>
    <v-row justify="center">
<v-card min-height="600px">
    <v-card-title>Hello</v-card-title>
    <v-card-text>
        <v-btn @click="showComponent('VisitCard')">Component 1</v-btn>
        <v-btn @click="showComponent('PatientTable')">Component 2</v-btn>
    </v-card-text>
    <transition name="component-fade" mode="out-in">
        <component v-bind:is="view"></component>
    </transition>
    <transition name="component-fade" mode="out-in">
        <v-btn>Button 1</v-btn>
    </transition>
</v-card>
    </v-row>
    </v-container>
</template>

<script>
import VisitCard from "@/components/Panels/DoctorPanel/MyVisits/VisitCard";
import PatientTable from "@/components/Panels/DoctorPanel/MyPatients/PatientTable";

export default {
    name: "MessengerTest",
    components: {'VisitCard': VisitCard, 'PatientTable': PatientTable},
    data(){
        return{
            view: ''
        }
    },
    methods: {
        showComponent(view){
            this.view = view
        }
    }
}
</script>

<style scoped>
.component-fade-enter-active, .component-fade-leave-active {
    transition: opacity 1s ease;
}
.component-fade-enter, .component-fade-leave-to
    /* .component-fade-leave-active for <2.1.8 */ {
    opacity: 0;
}
</style>