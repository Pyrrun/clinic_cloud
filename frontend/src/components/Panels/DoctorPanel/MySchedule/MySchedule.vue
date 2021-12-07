<template>
    <v-container fluid fill-height>
        <v-row justify="center">
            <v-col xs="11" lg="7" >
                <MyCalendar @dayChosen="dayChosen"
                            :events="events"
                            :breakDuration="breakDuration"
                            :visitsSubmitted="visitsSubmitted"
                            :date="date"
                            :stimulus="stimulus"
                            @editionModeChanged="changeEditionMode"
                            :editionModeDisabled="editionModeDisabled"
                >


                </MyCalendar>
            </v-col>

        <v-col  sm="10" xs="10" lg="4" md="9" >
            <SchedulePlanner
                :calendarDate="calendarDate"
                :isScheduleAlreadyDeclared="isScheduleAlreadyDeclared"
                :editionModeEnabled="editionModeEnabled"
                @newEvents="newEvents"
                @visitsSubmitted="setVisitsSubmitted"
                @breakDurationEvent="breakDurationEvent"
                @dateChangedInSchedulePlanner="changeDate"
                @editionCancelled="cancelEdition"
            ></SchedulePlanner>
        </v-col>
        </v-row>
    </v-container>
</template>

<script>
import MyCalendar from "@/components/Panels/DoctorPanel/MySchedule/MyCalendar";
import SchedulePlanner from "@/components/Panels/DoctorPanel/MySchedule/SchedulePlanner";
export default {
    name: "MySchedule",
    components: {MyCalendar, SchedulePlanner},
    data(){
        return{
            events: null,
            breakDuration: '',
            date: '',
            isScheduleAlreadyDeclared: false,
            visitsSubmitted : false,
            test: 0,
            calendarDate: '',
            stimulus: false,
            editionModeEnabled: false,
            editionModeDisabled: true

        }
    },
    methods: {
        newEvents(events, visitsSubmitted, date, stimulus){
            this.events = events
            this.visitsSubmitted = visitsSubmitted
            this.date = date
            this.stimulus = stimulus

        },
        breakDurationEvent(breakDuration, date){
            this.breakDuration = breakDuration
            this.date = date
        },
        setVisitsSubmitted(visitsSubmitted){
            this.visitsSubmitted = visitsSubmitted
        },
        dayChosen(isScheduleAlreadyDeclared, calendarDate){
            this.isScheduleAlreadyDeclared = isScheduleAlreadyDeclared
            this.calendarDate = calendarDate
        },
        changeDate(date){
            this.date = date
        },
        changeEditionMode(val){
            this.editionModeEnabled = val
        },
        cancelEdition(){
            this.editionModeDisabled = !this.editionModeDisabled
            this.visitsSubmitted = true
        }
    }


}
</script>

<style scoped>

</style>