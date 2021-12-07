<template>
    <v-container fluid class="mb-5">
        <v-row justify="center">
            <v-col xs="10" sm="10" md="7" lg="5">
                <v-card
                >
                    <v-card-title class="display-2">My visits</v-card-title>
                    <v-card-subtitle>Manage your scheduled visits</v-card-subtitle>
                    <v-card-text>
                        <v-divider></v-divider>
                    </v-card-text>

                    <v-tabs
                        centered
                        grow
                    >
                        <v-tab v-for="tab in tabs" :key="tab.title" @click="openTab(tab)">
                            {{ tab.title }}
                        </v-tab>
                        <v-dialog
                            max-width="300"
                            v-model="dialog"
                        >
                            <template v-slot:activator="{ on, attrs }" v-bind="attrs"
                                      v-on="on">
                                <v-tab @click="openCalendarTab">
                                    Calendar
                                </v-tab>
                            </template>
                            <template>
                                <DatePicker></DatePicker>
                            </template>
                        </v-dialog>

                        <v-tabs-slider color="primary"></v-tabs-slider>
                    </v-tabs>
                    <v-divider></v-divider>
                    <v-container ma-0 pa-0 v-show="tabIndex !== 2">
                        <v-carousel height="350px"
                                    hide-delimiter-background
                                    v-model="itemIndex"

                        >
                            <v-carousel-item v-for="(item, i) in items" :key="i"
                                             gradient="to top, rgba(0,0,0,.44), rgba(0,0,0,.44)"
                                             :src="imgSrc">
                                <v-container class="fill-height">
                                    <v-row justify="center">
                                        <strong style="color: white"
                                                class="display-4 font-weight-regular mr-6">{{ item.day }}</strong>
                                    </v-row>
                                    <v-row justify="center">
                                        <div style="color: white" class="headline font-weight-light">
                                            {{ item.dayString }}
                                        </div>
                                        <div style="color: white" class="text-uppercase font-weight-light">
                                            {{ item.month }} {{ item.year }}
                                        </div>
                                    </v-row>

                                </v-container>

                            </v-carousel-item>
                        </v-carousel>
                        <transition appear name="fade">
                        <v-container class="fill-height">
                            <v-card-text v-if="isVisitListNotEmpty">
                                <VisitTimeline :visits="visits"></VisitTimeline>
                            </v-card-text>
                            <v-card-text v-else class="text-center">
                                    You don't have any visits on {{ items[itemIndex].dayString }}.
                            </v-card-text>
                            <v-card-actions>
                                <v-btn text color="primary" @click="goBack">Return</v-btn>
                            </v-card-actions>
                        </v-container>
                        </transition>
                    </v-container>
                    <v-row justify="center">
                    </v-row>
                </v-card>
            </v-col>
        </v-row>
    </v-container>

</template>

<script>

import VisitTimeline from "@/components/Panels/DoctorPanel/MyVisits/VisitTimeline";
import {tokenAxios} from "@/axios";
import DatePicker from "@/components/Panels/ReceptionPanel/NewVisit/DatePicker";


export default {
    name: "MyVisits",
    components: {DatePicker, VisitTimeline},
    data() {
        return {
            imgSrc: 'https://cdn.vuetifyjs.com/images/cards/forest.jpg',
            itemIndex: 0,
            tabs: [{title: 'Today'}, {title: 'Tomorrow'}],
            days: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
            months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
            dayString: '',
            month: '',
            isVisitListNotEmpty: false,
            year: null,
            tabIndex: null,
            chosenTab: null,
            today: '',
            visits: null,
            isCalendarVisible: false,
            day: null,
            date: new Date(),
            dialog: false,
            items: [],
        }
    },
    created() {
        this.setDates()
    },
    watch: {
        itemIndex() {
            switch (this.itemIndex) {
                case 0:
                    this.imgSrc = 'https://cdn.vuetifyjs.com/images/cards/forest.jpg'
                    break
                case 1:
                    this.imgSrc = 'https://pix10.agoda.net/hotelImages/291813/-1/6ad23f7ff0b33321bee89ab707fb68e3.jpg?s=1024x768'
                    break
                case 2:
                    this.imgSrc = 'https://cdn.cheapism.com/images/011618_most_beautiful_views_in_the_world_sli.max-784x410_euInqPB.jpg'
                    break
                case 3:
                    this.imgSrc = 'https://www.travelhoundsusa.com/wp-content/uploads/2018/04/These-are-the-Most-Beautiful-Views-in-the-World-You-Should-See-Mauna-Kea-Beach.jpg'
                    break
            }
            this.getVisits()
        },

        '$store.state.date'(val) {
            if (val) {
                this.dialog = false
                this.items = []
                this.setDates(true)
                this.getVisits()
                this.$store.state.date = null
            }
        }
    },

    methods: {
        setDates(isCalendarUsed) {
            this.items = []
            for (let i = 0; i < 4; i++) {
                let fullDate
                if (isCalendarUsed) {
                    fullDate = new Date(this.$store.state.date)
                } else {
                    fullDate = new Date()
                }
                const newDate = fullDate.setDate(fullDate.getDate() + i)
                this.getDate(new Date(newDate))
                this.insertNewItem()
            }
            this.itemIndex = 0
            this.getVisits()
        },


        insertNewItem() {
            this.items.push(
                {
                    'day': this.day,
                    'dayString': this.dayString,
                    'month': this.month,
                    'year': this.year,
                    'date': this.today
                }
            )
        },

        goBack() {
            this.$emit('goBack')
        },

        openCalendarTab() {
            this.isVisitListNotEmpty = false
            this.dialog = true
        },

        getVisits() {
            tokenAxios.get('doctors/' + localStorage.getItem('id') + '/visits', {
                params: {
                    date: this.items[this.itemIndex].date,
                    finished: false
                }
            }).then((response) => {
                console.log(response.data)
                this.visits = response.data
                this.isVisitListNotEmpty = response.data.length !== 0;
            })
        },

        openTab(tab) {
            this.tabIndex = this.tabs.indexOf(tab)
            this.setDates()
            if (this.tabIndex === 1) {
                this.itemIndex = 1
            } else {
                this.itemIndex = 0
            }
            this.getVisits()
        },

        getDate(fullDate) {

            this.day = String(fullDate.getDate()).padStart(2, '0');
            const month = String(fullDate.getMonth() + 1).padStart(2, '0');
            this.year = fullDate.getFullYear();
            // date in yyyy-mm-dd format
            this.today = [this.year, month, this.day].join('-');
            const dateStringArray = fullDate.toDateString().split(' ');
            this.dayString = this.days.filter(element => element.startsWith(dateStringArray[0]))[0]
            this.month = this.months.filter(element => element.startsWith(dateStringArray[1]))[0]

        },

    }
}
</script>

<style scoped>

</style>