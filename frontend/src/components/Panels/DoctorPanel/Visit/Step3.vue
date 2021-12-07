<template>
    <div>
        <div class="text-center">
            <h1 class="display-1 font-weight-thin mb-3">
                <b>Step 3</b>
            </h1>
            <h2 class="display-1 font-weight-thin mb-3">
                <i>Referrals</i>
                <v-card-text>Manage your referrals</v-card-text>
            </h2>
        </div>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="addNewReferral">New referral</v-btn>
            <v-btn color="error" :disabled="!firstReferralStarted" @click="cancelReferral">
                <v-icon>
                    mdi-delete
                </v-icon>
            </v-btn>
        </v-card-actions>
        <v-tabs v-model="referralIndex">
            <v-tab :disabled="!firstReferralStarted"
                   v-for="(referral, i) in referrals"
                   :key="i"
                   @click="openReferral(referral)"
            >
                Referral {{ i + 1 }}
            </v-tab>
        </v-tabs>
        <v-tabs-items v-model="referralIndex">
            <v-tab-item :disabled="!firstReferralStarted" v-for="(referral, i) in referrals" :key="i">
                <v-card :disabled="!firstReferralStarted">
                    <v-card-title>Referral</v-card-title>
                    <v-card-subtitle>Date: {{ convertDate(new Date()) }}</v-card-subtitle>
                    <v-divider></v-divider>

                    <v-card-text>
                        <v-row justify="space-around">
                            <v-col cols="3" class="font-weight-bold text-button">
                                Patient:<br>
                                Doctor:<br>
                            </v-col>
                            <v-col cols="9" align="end" class="font-weight-light text-button">
                                {{ patientData.firstName }} {{ patientData.secondName }} {{ patientData.lastName }}<br>
                                {{ doctorFullName }}<br>
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
        <v-card-text>
            <v-select
                :disabled="!firstReferralStarted"
                v-model="dispensarySelect"
                :items="selectableDispensaries"
                label="Dispensary"
                persistent-hint
                return-object
                single-line
            >

            </v-select>
            <v-select
                :disabled="!firstReferralStarted"
                v-model="specializationSelect"
                :items="selectableSpecializations"
                label="Speciality"
                persistent-hint
                return-object
                single-line
            >
            </v-select>
            <v-row justify="space-between">
                <v-col cols="3">
                    <v-checkbox
                        :disabled="!firstReferralStarted"
                        class="ml-2"
                        v-model="urgent"
                        label="Urgent">
                    </v-checkbox>
                </v-col>
                <v-col cols="7">
                    <v-textarea
                        :disabled="!firstReferralStarted"
                        v-model="reason"
                        solo
                        label="Reason">
                    </v-textarea>
                </v-col>
            </v-row>
        </v-card-text>
        <v-card-actions>
            <v-btn color="primary" text @click="goBack">Return</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary"  @click="goToStep4" text>Save</v-btn>
        </v-card-actions>

    </div>
</template>

<script>
import {tokenAxios} from "@/axios";

export default {
    name: "Step3",
    props: {
        patientData: Object,
        doctorFullName: String
    },
    data() {
        return {
            referralIndex: 0,
            dispensarySelect: null,
            selectableDispensaries: [],
            dispensaries: [],
            specializationSelect: null,
            selectableSpecializations: [],
            specializations: [],
            urgent: false,
            reason: '',
            dispensary: '',
            specialization: '',
            referrals: [{dispensary: '', specialization: '', reason: '', priority: 'Normal'}],
            priority: '',
            firstReferralStarted: false,
            isFirstReferral: true,

        }
    },



    created() {
        tokenAxios.get('dispensaries')
            .then((response) => {
                console.log(response.data)
                this.dispensaries = response.data
                this.selectableDispensaries = response.data.map((dispensary) => {
                    return dispensary.name
                })
            })

    },


    methods: {
        goToStep4() {
            let isBadReferral = this.referrals.some((referral) => {
                return !referral.dispensary || !referral.specialization|| !referral.reason
            })
            if(isBadReferral && this.firstReferralStarted){
                this.$toast.error('Some referrals are missing required information')
                return
            }
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)
            setTimeout(() => {
                let referrals = []
                if (this.firstReferralStarted) {
                    referrals = this.referrals
                }
                this.$emit('thirdStepComplete', referrals)
            }, 200)

        },
        goBack() {
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)
            setTimeout(() => {
                this.$emit('goBack')
            }, 200)
        },
        openReferral(referral) {
            this.dispensary = referral.dispensary
            this.priority = referral.priority
            this.specialization = referral.specialization
            this.reason = referral.reason

        },
        convertDate(date) {
            return (new Date(date - (date).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
        },
        addNewReferral() {
            if (!this.firstReferralStarted) {
                this.firstReferralStarted = true
                return
            }
            let referral = {dispensary: '', specialization: '', reason: '', priority: 'Normal'}
            this.referrals.push(referral)
            this.referralIndex = this.referrals.indexOf(referral)
            this.dispensarySelect = ''
            this.specializationSelect = ''
            this.reason = ''
            this.priority = ''
            this.urgent = false
        },
        cancelReferral() {
            if (this.referrals.length === 1) {
                this.firstReferralStarted = false
                return
            }
            this.referrals.splice(this.referralIndex, 1)

        }
    },

    watch: {



        referralIndex() {
            let referral = this.referrals[this.referralIndex]
            this.urgent = referral.priority === 'Urgent';
            this.dispensarySelect = referral.dispensary
            this.specializationSelect = referral.specialization
        },

        specializationSelect(val) {
            this.referrals[this.referralIndex].specialization = val
        },

        reason(val) {
            this.referrals[this.referralIndex].reason = val
        },

        urgent(val) {
            if (val) {
                this.referrals[this.referralIndex].priority = 'Urgent'
                return
            }
            this.referrals[this.referralIndex].priority = 'Normal'
        },
        dispensarySelect(selectedItem) {
            this.referrals[this.referralIndex].dispensary = selectedItem
            if (selectedItem) {
                let dispensary = this.dispensaries.find((dispensary) => {
                    if (dispensary.name === selectedItem) {
                        return dispensary
                    }
                })
                tokenAxios.get('dispensaries/' + dispensary.id + '/specializations')
                    .then((response) => {
                        this.specializations = response.data
                        this.selectableSpecializations = response.data.map((specialization) => {
                            return specialization.name
                        })
                    })
            }
        }
    }

}
</script>

<style scoped>

</style>