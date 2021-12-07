<template>
    <v-card min-height="880">
        <v-card-title class="text-h2 text-sm-h2 text-md-h2 text-lg-h2">
            Notice board
            <v-spacer>
            </v-spacer>
            <v-btn color="primary" @click="newNoticeDialog = true">New notice</v-btn>
        </v-card-title>
        <v-card-subtitle>The most recent news from your colleagues</v-card-subtitle>
        <v-dialog max-width="600px" v-model="newNoticeDialog">
            <NewNotice @newNoticeSaved="saveNewNotice"></NewNotice>
        </v-dialog>
        <v-divider class="mx-4"></v-divider>
        <v-card-text v-if="!noticesEmpty">
            <div class="text-center">
                <v-btn @click="windowToggle(false)" :disabled="arrowUpDisabled" text>
                    <v-icon>
                        mdi-arrow-up
                    </v-icon>
                </v-btn>
            </div>
            <v-timeline class="mb-2 mt-2">
                <v-timeline-item
                    v-for="(notice, i) in notices"
                    :key="i"
                    icon="mdi-message-bulleted"
                >
                    <template v-slot:opposite>
                        <span><b>{{ notice.date }}</b></span>
                    </template>
                    <v-hover
                        v-slot="{ hover }"
                    >
                        <v-card min-height="180px" :elevation="hover ? 16 : 2" :class="notice.animation"
                                @animationend="notice.animation=false">
                            <v-list-item three-line>
                                <v-list-item-content>
                                    <div class="overline mb-4">
                                        {{ notice.date }}
                                    </div>
                                    <v-list-item-title class="headline mb-1">
                                        {{ notice.title }}
                                    </v-list-item-title>
                                    <v-list-item-subtitle> {{ notice.doctorName }}</v-list-item-subtitle>
                                </v-list-item-content>
                                <v-list-item-avatar
                                    size="80"
                                >
                                    <v-img :src="notice.img"></v-img>
                                </v-list-item-avatar>
                            </v-list-item>
                            <v-card-actions>
                                <v-btn text color="primary" @click="readMore(notice)" v-if="notice.content">Read more
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-hover>
                </v-timeline-item>
            </v-timeline>
            <div class="text-center">
                <v-btn text @click="windowToggle(true)" v-if="notices.length === 3" :disabled="arrowDownDisabled">
                    <v-icon>
                        mdi-arrow-down
                    </v-icon>
                </v-btn>
            </div>
        </v-card-text>
        <v-card-text v-else>
            No notices available.
        </v-card-text>
        <v-dialog max-width="500px" v-model="noticeContentDialog">
            <v-card>
                <v-list-item three-line>
                    <v-list-item-content>
                        <div class="overline mb-4">
                            {{ chosenNotice.date }}
                        </div>
                        <v-list-item-title class="headline mb-1">
                            {{ chosenNotice.title }}
                        </v-list-item-title>
                        <v-list-item-subtitle> {{ chosenNotice.doctorName }}</v-list-item-subtitle>
                    </v-list-item-content>
                    <v-list-item-avatar
                        size="80"
                    >
                        <v-img :src="chosenNotice.img"></v-img>
                    </v-list-item-avatar>
                </v-list-item>
                <v-card-text>
                    <v-divider></v-divider>
                </v-card-text>
                <v-card-text>{{ chosenNotice.content }}</v-card-text>
            </v-card>
        </v-dialog>
    </v-card>
</template>

<script>
import {tokenAxios} from "@/axios";
import NewNotice from "@/components/Panels/DoctorPanel/HomePage/NoticeBoard/NewNotice";


export default {
    name: "NoticeBoard",
    components: {NewNotice},
    data() {
        return {
            title: '',
            newNoticeDialog: false,
            noticeContentDialog: false,
            notices: [],
            olderTimeline: false,
            noticesEmpty: true,
            show: true,
            arrowUpDisabled: true,
            allLoadedNotices: [],
            noticeGroupIndex: 0,
            arrowDownDisabled: false,
            fourNoticesDetected: false,
            chosenNotice: {},
            canMoreOlderEventsBeFound: false,
        }
    },


    watch: {
        '$store.state.webSocketConnectionEstablished'(val) {
            this.setSubscription(val)
        }
    },

    created() {

        tokenAxios.get('notices').then((response) => {
            if (response.data.length === 0) {
                this.noticesEmpty = true
                return
            }
            this.noticesEmpty = false
            console.log(response.data)
            this.addNotices(response)
        })
        this.setSubscription(this.$store.state.webSocketConnectionEstablished)
    },

    destroyed() {
        this.$store.state.stompClient.unsubscribe('notice-board')
    },

    methods: {
        setSubscription(val){
            if (val) {
                this.$store.state.stompClient.subscribe('/topic/notice-board', (message) => {
                    if (!message.body) {
                        return
                    }
                    this.addNewNotice(message.body)
                }, {id: 'notice-board'})
            }
        },

        addNewNotice(notice) {
            this.arrowUpDisabled = true
            this.arrowDownDisabled = true
            this.newNoticeDialog = false
            this.noticesEmpty = false
            notice = JSON.parse(notice)
            notice = {
                title: notice.title,
                content: notice.content,
                date: notice.date.substring(0, 10) + ' ' + notice.date.substring(11),
                img: notice.doctor.img,
                doctorName: notice.doctor.firstName + ' ' + notice.doctor.lastName,
                animation: 'fade'
            }
            switch (this.notices.length) {
                case 0:
                    this.notices.push(notice)
                    this.allLoadedNotices.push(Object.assign({}, notice))
                    this.$toast.info('New notice has been added to your notice board', {
                        duration: 0
                    })
                    return
                case 3:
                    this.notices[0].animation = this.notices[2].animation = 'flip'
                    this.notices[1].animation = 'flipReverse'
                    this.arrowDownDisabled = true
                    break
                default:
                    this.notices.splice(0, 0, notice)
                    this.allLoadedNotices.splice(0, 0, Object.assign({}, notice))
                    this.$toast.info('New notice has been added to your notice board', {
                        duration: 0
                    })
                    return
            }
            setTimeout(() => {
                let noticeCopy1 = Object.assign({}, this.allLoadedNotices[0])
                let noticeCopy2 = Object.assign({}, this.allLoadedNotices[1])
                this.notices.filter((notice) => {
                    notice.title = ''
                    notice.content = ''
                    notice.doctorName = ''
                    notice.date = ''
                    notice.img = ''
                })
                setTimeout(() => {
                    this.notices[0].title = notice.title
                    this.notices[0].content = notice.content
                    this.notices[0].doctorName = notice.doctorName
                    this.notices[0].date = notice.date
                    this.notices[0].img = notice.img

                    this.notices[1].title = noticeCopy1.title
                    this.notices[1].content = noticeCopy1.content
                    this.notices[1].doctorName = noticeCopy1.doctorName
                    this.notices[1].date = noticeCopy1.date
                    this.notices[1].img = noticeCopy1.img

                    this.notices[2].title = noticeCopy2.title
                    this.notices[2].content = noticeCopy2.content
                    this.notices[2].doctorName = noticeCopy2.doctorName
                    this.notices[2].date = noticeCopy2.date
                    this.notices[2].img = noticeCopy2.img

                    this.allLoadedNotices.splice(0, 0, Object.assign({}, notice))
                    this.noticeGroupIndex = 0
                    this.arrowUpDisabled = true
                    this.arrowDownDisabled = false
                    this.$toast.info('New notice has been added to your notice board', {
                        duration: 0
                    })
                    this.allLoadedNotices.splice(3)
                    console.log(this.allLoadedNotices)
                }, 1200)

            }, 200)
        },


        createNewNotices(newNotices) {
            if (newNotices.length === 4) {
                newNotices.splice(3, 1)
                this.canMoreOlderEventsBeFound = true
            } else {
                this.canMoreOlderEventsBeFound = false
                this.arrowDownDisabled = true
            }
            return newNotices.map((notice) => {
                return {
                    title: notice.title,
                    content: notice.content,
                    date: notice.date.substring(0, 10) + ' ' + notice.date.substring(11),
                    img: notice.doctor.img,
                    doctorName: notice.doctor.firstName + ' ' + notice.doctor.lastName,
                    animation: false
                }
            })
        },


        windowToggle(areOlderNoticesSearched) {
            let newNotices = []
            //   let alreadyLoaded = false
            this.arrowUpDisabled = true
            this.arrowDownDisabled = true
            // if arrowDown was pressed
            if (areOlderNoticesSearched) {
                this.noticeGroupIndex += 3
                if (this.allLoadedNotices.length === this.noticeGroupIndex) {
                    tokenAxios.get('notices/before', {
                        params: {
                            date: this.notices[2].date
                        }
                    }).then((response) => {
                        newNotices = this.createNewNotices(response.data)
                        newNotices.filter((notice) => {
                            this.allLoadedNotices.push(notice)
                        })
                    })
                } else {
                    // alreadyLoaded = true
                    switch (this.allLoadedNotices.length) {
                        case this.noticeGroupIndex + 1:
                            newNotices = [
                                this.allLoadedNotices[this.noticeGroupIndex]
                            ]
                            break
                        case this.noticeGroupIndex + 2:
                            newNotices = [
                                this.allLoadedNotices[this.noticeGroupIndex],
                                this.allLoadedNotices[this.noticeGroupIndex + 1],
                            ]
                            break
                        default:
                            if (this.allLoadedNotices.length === this.noticeGroupIndex + 4) {
                                this.canMoreOlderEventsBeFound = true
                            }
                            newNotices = [
                                this.allLoadedNotices[this.noticeGroupIndex],
                                this.allLoadedNotices[this.noticeGroupIndex + 1],
                                this.allLoadedNotices[this.noticeGroupIndex + 2]
                            ]
                    }
                }
            } else {
                this.noticeGroupIndex -= 3;
                newNotices = [
                    this.allLoadedNotices[this.noticeGroupIndex],
                    this.allLoadedNotices[this.noticeGroupIndex + 1],
                    this.allLoadedNotices[this.noticeGroupIndex + 2]
                ]
            }
            this.notices[0].animation = this.notices[2].animation = 'flip'
            this.notices[1].animation = 'flipReverse'
            setTimeout(() => {
                this.notices.filter((notice) => {
                    notice.title = ''
                    notice.content = ''
                    notice.doctorName = ''
                    notice.date = ''
                    notice.img = ''
                })
                setTimeout(() => {
                    let i = 0;
                    newNotices.filter((newNotice) => {
                        this.notices[i].title = newNotice.title
                        this.notices[i].content = newNotice.content
                        this.notices[i].doctorName = newNotice.doctorName
                        this.notices[i].date = newNotice.date
                        this.notices[i].img = newNotice.img
                        i++
                    })
                    setTimeout(() => {
                        if (this.noticeGroupIndex !== 0) {
                            this.arrowUpDisabled = false
                        }
                        // this means we've chosen the arrowUp or there are more events to be loaded or we're browsing through already loaded events
                        if (newNotices.length === 3 && (!areOlderNoticesSearched || this.canMoreOlderEventsBeFound)) {
                            this.arrowDownDisabled = false
                        }
                    }, 400)
                }, 1200)

            }, 200)
        },

        readMore(notice) {
            this.noticeContentDialog = true
            this.chosenNotice = notice
        },

        saveNewNotice(notice) {
            this.$store.state.stompClient.send('/app/new-notice', JSON.stringify(notice))
            this.newNoticeDialog = false
        },

        addNotices(response) {
            let notices = this.createNewNotices(response.data)
            this.allLoadedNotices = Object.assign(this.allLoadedNotices, notices)
            this.notices = notices.map((notice) => {
                return Object.assign({}, notice)
            })
            if (this.notices.length === 3) {
                this.notices[1].transitionName = 'flipReverse'
            }
            this.noticeGroupIndex = 0

        }
    },

}
</script>

<style scoped>

@keyframes animate-flip {
    50% {
        transform: translateX(-500px) rotateY(180deg);
    }
    100% {
        transform: translateX(0) rotateY(0);
    }
}

@keyframes animate-fade {
    0% {
        opacity: 0;
    }
    100% {
        opacity: 100;
    }
}

@keyframes animate-flipReverse {
    50% {
        transform: translateX(500px) rotateY(180deg);
    }
    100% {
        transform: translateX(0) rotateY(0);
    }
}

.fade {
    animation-name: animate-fade;
    animation-duration: 2s;
    animation-direction: alternate;
}

.flip {
    animation-name: animate-flip;
    animation-duration: 2s;
    animation-direction: alternate;
}

.flipReverse {
    animation-name: animate-flipReverse;
    animation-duration: 2s;
    animation-direction: alternate;
}


</style>