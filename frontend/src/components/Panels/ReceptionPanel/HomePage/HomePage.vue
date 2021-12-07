<template>
    <v-container fluid>
        <v-row justify="center">
            <v-col xs="12" md="10" lg="6">
                <Activities @cardChosen="cardChosen"></Activities>
            </v-col>
            <v-col xs="12" md="10" lg="6">
                <NoticeBoard></NoticeBoard>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>


import NoticeBoard from "@/components/Panels/DoctorPanel/HomePage/NoticeBoard/NoticeBoard";
import Activities from "@/components/Panels/ReceptionPanel/HomePage/Activities";

export default {
    name: "HomePage",
    components: { Activities, NoticeBoard},

    methods: {
        cardChosen(index){
            this.$emit('cardChosen', index)
        },
        beginVisit(patientData, selectedVisitDate, selectedVisitDuration){
            this.$emit('visitStarted', patientData, selectedVisitDate, selectedVisitDuration)
        }
    }


}

</script>

<style scoped>

</style>