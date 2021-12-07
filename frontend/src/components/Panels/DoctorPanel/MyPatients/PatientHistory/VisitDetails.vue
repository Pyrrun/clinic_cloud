<template>
    <v-card min-height="300px">
        <v-list-item three-line>
            <v-list-item-content>
                <div class="overline mb-4">
                    visit details
                </div>
                <v-list-item-title class="headline mb-1">
                    Patient: {{ visitData.patient.firstName }} {{ visitData.patient.secondName }} {{ visitData.patient.lastName }}
                </v-list-item-title>
                <v-list-item-subtitle class="body-1 mb-1 font-italic">

                    Doctor: {{ visitData.doctorFullName }}
                </v-list-item-subtitle>
                <v-list-item-subtitle>Date: {{ visitData.date }}</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-avatar
                size="80"
            >
                <v-img :src="visitData.doctorImg" :alt="visitData.doctorFullName"></v-img>
            </v-list-item-avatar>
        </v-list-item>
        <v-card-text>
            <v-tabs grow v-model="tabIndex">
                <v-tab>Interview</v-tab>
                <v-tab>Research</v-tab>
                <v-tab>Referrals</v-tab>
                <v-tab>Medicine</v-tab>
            </v-tabs>
            <v-tabs-items v-model="tabIndex">
                <v-tab-item>
                    <v-card-text>
                        <span v-if="visitData.interview">{{ visitData.interview }}</span>
                        <span v-else>No interview details were provided during the visit</span>
                    </v-card-text>
                </v-tab-item>
                <v-tab-item>
                    <v-card-text>
                        <span v-if="visitData.research">{{ visitData.research }}</span>
                        <span v-else>No research was made during the visit</span>
                    </v-card-text>
                </v-tab-item>
                <v-tab-item>
                    <v-card-text>
                        <div v-show="visitData.referrals.length > 0">
                        <v-tabs v-model="referralIndex">
                            <v-tab
                                   v-for="(referral, i) in visitData.referrals"
                                   :key="i"
                            >
                                Referral {{ i + 1 }}
                            </v-tab>
                        </v-tabs>
                        <v-tabs-items v-model="referralIndex">
                            <v-tab-item v-for="(referral, i) in visitData.referrals" :key="i">
                                <v-card>
                                    <v-card-title>Referral</v-card-title>
                                    <v-card-subtitle>Date: {{visitData.date}}</v-card-subtitle>
                                    <v-divider></v-divider>
                                    <v-card-text>
                                        <v-row justify="space-around">
                                            <v-col cols="3" class="font-weight-bold text-button">
                                                Patient:<br>
                                                Doctor:<br>
                                            </v-col>
                                            <v-col cols="9" align="end" class="font-weight-light text-button">
                                                {{ visitData.patient.firstName }} {{ visitData.patient.secondName }} {{ visitData.patient.lastName }}<br>
                                                {{ visitData.doctorFullName }}<br>
                                            </v-col>
                                        </v-row>
                                    </v-card-text>
                                    <v-divider></v-divider>
                                    <v-card-text>
                                        <v-row justify="space-around">
                                            <v-col cols="3" class="font-weight-bold text-button">
                                                Code:<br>
                                                Dispensary:<br>
                                                Priority:<br>
                                                Speciality:<br>
                                            </v-col>
                                            <v-col cols="9" align="end" class="font-weight-light text-button">
                                                30812<br>
                                                {{ referral.dispensary }}<br>
                                                {{ referral.priority }}<br>
                                                {{ referral.specialization }}<br>
                                            </v-col>
                                        </v-row>
                                        <span class="font-weight-bold text-button">Reason:</span> <span
                                        class="font-weight-light text-button">{{ referral.reason }}</span>
                                    </v-card-text>

                                </v-card>
                            </v-tab-item>
                        </v-tabs-items>
                        </div>
                        <div v-show="visitData.referrals.length === 0">
                            No referrals were assigned during the visit
                        </div>
                    </v-card-text>
                </v-tab-item>
                <v-tab-item>
                    <v-card-text>
                        <div v-show="visitData.medications.length > 0">
                        <v-tabs  v-model="medicationIndex">
                            <v-tab
                                   v-for="(medication, i) in visitData.medications"
                                   :key="i"
                            >
                                Medication {{ i + 1 }}
                            </v-tab>
                        </v-tabs>
                        <v-tabs-items v-model="medicationIndex">
                            <v-tab-item v-for="(medication, i) in visitData.medications"
                                        :key="i">
                                <div v-if="medication.name">
                                    <v-card-text>
                                        <v-row>
                                            <v-col cols="4">
                                                <v-img  :src="imgDir + medication.imageName" ></v-img>
                                            </v-col>
                                            <v-col    cols="8" class="text-center">
                                                <span class="display-2">{{medication.name}}</span><br>
                                                <span class="body-1">{{medication.description}}</span><br>
                                            </v-col>
                                        </v-row>
                                    </v-card-text>
                                    <v-card-text>
                                        <v-col cols="4">
                                            <span class="body-1"><b>Quantity:</b> {{medication.quantity}}</span>
                                        </v-col>
                                        <v-col>
                                            <span class="body-1"><b>Instructions:</b> <span v-if="medication.details">{{medication.details}}</span> <span v-else> No details were provided for this medication</span></span>
                                        </v-col>
                                    </v-card-text>

                                </div>
                            </v-tab-item>
                        </v-tabs-items>
                        </div>
                        <div v-show="visitData.medications.length === 0">
                            No medications were prescripted during the visit
                        </div>
                    </v-card-text>
                </v-tab-item>
            </v-tabs-items>

        </v-card-text>
        <v-card-actions v-if="isVisitBeingCreated">
            <v-spacer></v-spacer>
            <v-btn text color="primary" large @click="openConfirmDialog">Finish visit</v-btn>
        </v-card-actions>
        <v-dialog v-model="confirmDialog" max-width="400px">
            <v-card>
                <v-card>
                    <v-card-title>
                        Visit save confirmation
                    </v-card-title>
                    <v-card-text>
                        Are you sure you want to finish your visit? You will not be able to provide any changes anymore.
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="closeConfirmDialog">
                            Cancel
                        </v-btn>
                        <v-btn color="blue darken-1" :loading="loading" text @click="finishVisit">Yes,
                            I'm sure
                        </v-btn>
                        <v-spacer></v-spacer>
                    </v-card-actions>
                </v-card>
            </v-card>
        </v-dialog>
    </v-card>
</template>

<script>
import {tokenAxios} from "@/axios";

export default {
    name: "VisitDetailsComponent",
    props: {
        visitData: Object,
        isVisitBeingCreated: Boolean
    },
    data() {
        return {
            loading: false,
            medicationIndex: 0,
            imgDir: `${process.env.VUE_APP_BASE_URL}/images/medications/`,
            tabIndex: 0,
            referralIndex: 0,
            confirmDialog: false
        }
    },


    methods: {
        openConfirmDialog(){
            this.confirmDialog = true
        },
        closeConfirmDialog(){
            this.confirmDialog = false
        },
        finishVisit(){
            this.loading = true
            let medicationIds = []
            this.visitData.medications.filter((medication) => {
               medicationIds.push(medication.id)
            })
            tokenAxios.put('visits', {
                date: this.visitData.date,
                doctorId: localStorage.getItem('id'),
                patientId: this.visitData.patient.id,
                research: this.visitData.research,
                interview: this.visitData.interview,
                medicationIds: medicationIds,
                referrals: this.visitData.referrals
            }).then(() => {
                this.$emit('visitFinished')
                this.$toast.success('Your visit has been finished successfully.')
            }).catch(() => this.$toast.error('For some reason we have failed to save your visit.'))
                .finally(() => {
                this.loading = false
            })
        }

    }
}
</script>

<style scoped>

</style>