<template>
    <v-timeline

    >
        <v-timeline-item
            v-for="item in myVisitArray"
            :key="item.index"
            color="primary"
            small
        >
            <span slot="opposite" class="body-1">{{ item.hour }}</span>
            <v-card>
                <v-card-title>
                    {{ item.patientName }}
                </v-card-title>
                <v-card-subtitle v-if="item.reason">
                   {{item.reason}}
                </v-card-subtitle>
                <v-card-subtitle v-else>
                    No reason specified
                </v-card-subtitle>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click.stop="showPatientDetails(item)">Details</v-btn>
                </v-card-actions>
            </v-card>
        </v-timeline-item>
        <v-dialog max-width="600" v-model="patientDetails">
            <PatientHistory :patientData="patientData"></PatientHistory>
        </v-dialog>
    </v-timeline>
</template>

<script>




import PatientHistory from "@/components/Panels/DoctorPanel/MyPatients/PatientHistory/PatientHistory";

export default {
    name: "VisitTimeline",
    components:{PatientHistory},
    data() {
        return {
            myVisitArray: [],
            patientDetails: false,
            patientData: null
        }
    },
    props: {
        visits: Array
    },
    methods: {
        showPatientDetails(item) {
            this.patientData = item.patientData
            this.patientDetails = true
        }
    },
    created() {
        this.visits.filter((element) => {
            const date = new Date(element.date)
            const hour = date.toTimeString().split(' ')[0]
            this.myVisitArray.push({
                hour: hour,
                patientName: element.patient.firstName + ' ' + element.patient.secondName + ' ' + element.patient.lastName,
                patientData: element.patient
            })
        })

    }

}
</script>

<style scoped>

</style>