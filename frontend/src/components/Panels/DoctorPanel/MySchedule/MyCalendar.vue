<template>

    <v-card shaped>
        <v-card-text>
            <v-toolbar flat class="mt-3">


                <v-btn color="primary" :disabled="editionModeEnabled" text outlined @click="chooseCalendarType('day')">Day</v-btn>
                <v-btn color="primary" :disabled="editionModeEnabled" text outlined @click="chooseCalendarType('week')">Week</v-btn>
                <v-btn color="primary" :disabled="editionModeEnabled" text outlined @click="chooseCalendarType('month')">Month</v-btn>
                <v-btn
                    fab
                    text
                    small
                    color="grey darken-2"
                    @click="prev"
                    :disabled="editionModeEnabled"
                >
                    <v-icon small>
                        mdi-chevron-left
                    </v-icon>
                </v-btn>
                <v-btn
                    fab
                    text
                    small
                    color="grey darken-2"
                    @click="next"
                    :disabled="editionModeEnabled"
                >
                    <v-icon small>
                        mdi-chevron-right
                    </v-icon>
                </v-btn>
                <v-toolbar-title v-if="$refs.calendar">
                    {{ $refs.calendar.title }}
                </v-toolbar-title>
            </v-toolbar>
            <v-row justify="end">

                <v-btn color="primary" :disabled="!isScheduleAlreadyDeclared" @click="editEvents" class="mr-2">edit
                </v-btn>
                <v-btn color="error" :disabled="!isScheduleAlreadyDeclared" :loading="deleteLoading" class="mr-10"
                       @click="deleteEvents">
                    <v-icon>
                        mdi-delete
                    </v-icon>
                </v-btn>

            </v-row>

            <v-sheet height="500">
                <v-calendar
                    class="mt-5"
                    ref="calendar"
                    v-model="focus"
                    color="primary"
                    @click:more="viewDay"
                    @click:date="viewDay"
                    :type="type"
                    :interval-format="intervalFormat"
                    :first-interval=5
                    :interval-count=18
                    :events="visits"
                    :event-color="getEventColor"
                    @click:event="showEvent"

                ></v-calendar>
                <v-menu
                    v-model="selectedOpen"
                    :close-on-content-click="false"
                    :activator="selectedElement"
                    offset-x
                    max-width="400px"
                >
                    <v-card

                        color="grey lighten-4"
                        flat
                    >
                        <v-toolbar
                            :color="selectedEvent.color"
                            dark
                        >
                            <v-btn icon>
                                <v-icon>mdi-pencil</v-icon>
                            </v-btn>
                            <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-btn icon>
                                <v-icon>mdi-heart</v-icon>
                            </v-btn>
                            <v-btn icon>
                                <v-icon>mdi-dots-vertical</v-icon>
                            </v-btn>
                        </v-toolbar>
                        <v-card-text>
                            <span v-html="selectedEvent.details"></span>
                        </v-card-text>
                        <v-card-actions>
                            <v-btn
                                text
                                color="secondary"
                                @click="selectedOpen = false"
                            >
                                Cancel
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-menu>
            </v-sheet>
        </v-card-text>

    </v-card>

</template>

<script>




import {tokenAxios} from "@/axios";

export default {
    name: "MyCalendar",

    props: {
        events: Array,
        breakDuration: String,
        date: String,
        visitsSubmitted: Boolean,
        stimulus: Boolean,
        editionModeDisabled: Boolean

    },


    data() {
        return {
            type: 'week',
            focus: '',
            temporaryEvents: [],
            temporaryEventsCopy: [],
            visits: [],
            dragEvent: null,
            dragStart: null,
            createEvent: null,
            createStart: null,
            extendOriginal: null,
            selectedEvent: {},
            selectedElement: null,
            selectedOpen: false,
            isScheduleAlreadyDeclared: false,
            isDayZoomed: true,
            deleteLoading: false,
            editionModeEnabled: false,


        }
    },


    computed: {
        areVisitsSubmitted() {
            return this.visitsSubmitted
        }

    },


    watch: {

        stimulus() {
            this.putEventsIntoCalendar(this.events)
        },

        editionModeDisabled(){
            this.editionModeEnabled = false
            this.$emit('editionModeChanged', this.editionModeEnabled)
            this.temporaryEvents.filter((value) => {
                this.visits.splice(this.visits.indexOf(value), 1)
            })
            this.temporaryEvents = []
            this.temporaryEventsCopy.filter((event) => {
                event.color = '#2196F3'
            })
            this.putEventsIntoCalendar(this.temporaryEventsCopy)
            this.isScheduleAlreadyDeclared = true
            this.$emit('dayChosen', this.isScheduleAlreadyDeclared, this.date)


        },


        date() {
            if (this.isDayZoomed) {
                this.type = 'day'
            }
            this.focus = this.date
        },

    },
    mounted() {
        this.$refs.calendar.checkChange()
    }
    ,
    methods: {
        putEventsIntoCalendar(events) {
            if (this.areVisitsSubmitted) {
                if (this.temporaryEvents.length === 0) {
                    this.visits = this.visits.concat(events)
                    return
                }
                this.type = 'day'
                this.temporaryEvents.filter((event) => {
                    event.color = '#2196F3'
                })
                this.temporaryEvents = []
                if(this.editionModeEnabled){
                    this.editionModeEnabled = false
                    this.$emit('editionModeChanged', this.editionModeEnabled)
                }
            }
            if (!this.date) {
                return
            }
            // if temporaryEvents are present on that date, move on
            if (this.checkIfScheduleAlreadyDeclared(this.temporaryEvents, this.date)) {
                this.isScheduleAlreadyDeclared = false
            }

            // if temporaryEvents aren't present on that date, check if visits are present
            else {
                this.isScheduleAlreadyDeclared = this.checkIfScheduleAlreadyDeclared(this.visits, this.date)
                // if visits are present, block schedule planner
                if (this.isScheduleAlreadyDeclared) {
                    this.$emit('dayChosen', this.isScheduleAlreadyDeclared, this.date)
                    this.isDayZoomed = true
                    return
                }
            }
            this.$emit('dayChosen', this.isScheduleAlreadyDeclared, this.date)
            if (this.temporaryEvents.length > 0) {
                this.temporaryEvents.filter((value) => {
                    this.visits.splice(this.visits.indexOf(value), 1)
                })
            }
            if (!events) {
                this.temporaryEvents = []
                return
            }
            this.visits = this.visits.concat(events)
            this.temporaryEvents = events.map((event) => {
                event.color='#3CB371'
                return event
            })

        },

        convertDate(date){
            return (new Date(date - (date).getTimezoneOffset() * 60000)).toISOString().substr(0,10)
        },

        chooseCalendarType(type) {
            this.type = type
            let date = null
            if (type !== 'day') {
                this.isScheduleAlreadyDeclared = false
                if (this.temporaryEvents.length > 0) {
                    date = this.convertDate(new Date(this.temporaryEvents[0].start))
                }
                this.isDayZoomed = false
            }
            else{
                date = this.date
            }

            this.$emit('dayChosen', this.isScheduleAlreadyDeclared, date)
        },


        showEvent({nativeEvent, event}) {
            const open = () => {
                this.selectedEvent = event
                this.selectedElement = nativeEvent.target
                setTimeout(() => {
                    this.selectedOpen = true
                }, 10)
            }

            if (this.selectedOpen) {
                this.selectedOpen = false
                setTimeout(open, 10)
            } else {
                open()
            }
            nativeEvent.stopPropagation()
        },

        getEventColor(event) {
            const rgb = parseInt(event.color.substring(1), 16)
            const r = (rgb >> 16) & 0xFF
            const g = (rgb >> 8) & 0xFF
            const b = (rgb >> 0) & 0xFF

            return event === this.dragEvent
                ? `rgba(${r}, ${g}, ${b}, 0.7)`
                : event === this.createEvent
                    ? `rgba(${r}, ${g}, ${b}, 0.7)`
                    : event.color
        },


        intervalFormat(interval) {
            return interval.time
        },



        deleteEvents() {
            this.deleteLoading = true
            if(this.temporaryEvents.length > 0){
                this.temporaryEvents.filter((element) => {
                    this.visits.splice(this.visits.indexOf(element), 1)
                })
                this.temporaryEvents = []
            }
            let chosenEvents = this.findChosenEvents()
            tokenAxios.delete('doctors/' + localStorage.getItem('id') + '/schedules', {
                params: {
                    date: this.date
                }
            }).then(() => {
                chosenEvents.filter((element) => {
                    this.visits.splice(this.visits.indexOf(element), 1)
                })
            }).finally(() => {
                this.isScheduleAlreadyDeclared = false
                this.deleteLoading = false
                this.$emit('dayChosen', this.isScheduleAlreadyDeclared, this.date)
                this.$toast.success('Visits deleted successfully')
            })

        },

        findChosenEvents() {
           return this.visits.filter((event) => {
                let myDate = new Date(this.date)
                let eventDate = new Date(event.start)
                if (eventDate.getDate() === myDate.getDate()
                    && eventDate.getMonth() === myDate.getMonth()
                    && eventDate.getFullYear() === myDate.getFullYear()) {
                    return event
                }
            })
        },



        editEvents() {
            if(this.temporaryEvents.length > 0){
                this.temporaryEvents.filter((element) => {
                    this.visits.splice(this.visits.indexOf(element), 1)
                })
                this.temporaryEvents = []
            }
            this.temporaryEvents = this.findChosenEvents()
            this.temporaryEvents.filter((event) => {
                event.color = '#3CB371'
            })
            this.temporaryEventsCopy = this.temporaryEvents.map((event) => {
                return Object.assign({}, event)
            })
            this.isScheduleAlreadyDeclared = false
            this.$emit('dayChosen', this.isScheduleAlreadyDeclared, this.date)
            this.editionModeEnabled = true
            this.$emit('editionModeChanged', this.editionModeEnabled)

        },


        checkIfScheduleAlreadyDeclared(arr, date) {
            let myDate = new Date(date)
            return arr.some(function (event) {
                let eventDate = new Date(event.start)
                return eventDate.getDate() === myDate.getDate()
                    && eventDate.getMonth() === myDate.getMonth()
                    && eventDate.getFullYear() === myDate.getFullYear()
            });
        },


        viewDay({date}) {
            this.type = 'day'
            this.focus = date
            if (this.checkIfScheduleAlreadyDeclared(this.temporaryEvents, date)) {
                this.isScheduleAlreadyDeclared = false
            } else if (this.checkIfScheduleAlreadyDeclared(this.visits, date)) {
                this.isScheduleAlreadyDeclared = true
            }
            this.$emit('dayChosen', this.isScheduleAlreadyDeclared, date)

        },
        prev() {
            this.$refs.calendar.prev()
        },
        next() {
            this.$refs.calendar.next()
        },
    }
}
</script>

<style scoped>

</style>