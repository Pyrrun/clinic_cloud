<template>
    <v-card-text>
        <v-row justify="center">
            <v-col cols="10">
                <v-dialog v-model="visitDetailsDialog" max-width="600">
                    <VisitDetails :visitData="visitData"></VisitDetails>
                </v-dialog>
                <v-data-table
                    :headers="headers"
                    :items="visits"
                    :single-select="singleSelect"
                    item-key="name"
                    :search="search"
                >
                    <template v-slot:item="{ item }">
                        <tr>
                            <td class="text-xs-right">{{ item.date }}</td>
                            <td class="text-xs-right">{{patientData.firstName}} {{patientData.secondName}} {{patientData.lastName}}</td>
                            <td class="text-xs-right">{{ item.doctorFullName }}</td>
                            <td>
                                <v-btn @click.stop="openVisitDetailsDialog(item)" x-small color="primary">Details
                                </v-btn>
                            </td>
                        </tr>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
    </v-card-text>
</template>

<script>
import {tokenAxios} from "@/axios";
import VisitDetails from "@/components/Panels/DoctorPanel/MyPatients/PatientHistory/VisitDetails";

export default {
    name: "VisitTable",
    components: {VisitDetails},
    props: {
        patientData: Object,
        tabIndex: Number
    },
    data() {
        return {
            search: '',
            singleSelect: true,
            visits: [],
            visitDetailsDialog: false,
            visitData: null
        }
    },
    methods: {

        addVisit(element){
            let visitDetails = {
                date: element.visit.date.replace(/T/g, ' '),
                doctorFullName: element.visit.doctor.firstName + ' ' + element.visit.doctor.secondName + ' ' + element.visit.doctor.lastName,
                doctorImg: element.visit.doctor.img,
                referrals: element.referrals,
                patient: element.visit.patient,
                medications: element.prescription ? element.prescription.medicationList : [],
                research: element.examination ? element.examination.research : '',
                interview: element.examination ? element.examination.interview : ''
            }
            // if(element.prescription){ visitDetails.medications = element.prescription.medicationList
            // if(element.examination){
            //     visitDetails.interview = element.examination.interview
            //     visitDetails.research = element.examination.research
            // }
            this.visits.push(visitDetails)

        },


        openVisitDetailsDialog(item) {
            this.visitData = item
            this.visitData['patient'] = this.patientData
            console.log(this.visitData)
            this.visitDetailsDialog = true
        },
        getAllVisitsForPatient() {
            tokenAxios.get('patients/' + this.patientData.id + '/visits')
                .then((response) => {
                    console.log(response.data)
                    response.data.forEach((element) => {
                        this.addVisit(element)
                    })
                })
        },
        getAllVisitsForPatientAndDoctor() {
            tokenAxios.get('doctors/' + localStorage.getItem('id') + '/visits', {
                params: {
                    patientId: this.patientData.id
                }
            }).then((response) => {
                response.data.forEach((element) => {
                   this.addVisit(element)
                })
            })
        }
    },
    watch: {
        tabIndex(val) {
            this.visits = []
            if (val === 1) {
                this.getAllVisitsForPatient()
            } else if (val === 2) {
                this.getAllVisitsForPatientAndDoctor()
            }
        }
    },
    created() {
        if (this.tabIndex === 1) {
            this.getAllVisitsForPatient()

        } else if (this.tabIndex === 2) {
            this.getAllVisitsForPatientAndDoctor()
        }
    },
    computed: {
        headers() {
            return [
                {
                    text: 'Date',
                    align: 'start',
                    sortable: true,
                    value: 'date',
                },
                {
                    text: 'Patient',
                    value: 'patientFullName',

                },
                {
                    text: 'Doctor',
                    value: 'doctorFullName',

                },
                {
                    text: 'Details',
                    value: 'details',

                },


            ]
        },
    }
}
</script>

<style scoped>

</style>