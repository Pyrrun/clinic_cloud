<template>
    <v-app>
        <v-app-bar
            class="indigo lighten-1 white--text text-center"
            app
            clipped-right
            clipped-left
            height="72"

        >
            <v-img
                :src="logo"
                max-height="90px"
                max-width="90px"
                contain
                class="mt-3"
            >
            </v-img>
            <v-spacer></v-spacer>
            <v-btn text @click="goBack">
                <v-icon color="white">
                    mdi-home
                </v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <span class="mr-3">
                <v-avatar>
                <v-img :src="img"></v-img>
            </v-avatar>
                <span class="ml-2">{{ firstName }}</span>
            </span>

        </v-app-bar>
        <v-navigation-drawer
            app
            clipped
            right
        >
            <v-list>
                <v-subheader>RECENT MESSAGES</v-subheader>
                    <v-list-item
                        v-for="(recentMessage, i) in recentMessages"
                        :key="i"
                        link
                        three-line
                    >
                        <v-list-item-avatar>
                            <v-img :src="recentMessage.senderImg"></v-img>
                        </v-list-item-avatar>
                        <v-list-item-content>
                            <v-list-item-title class="font-weight-bold">{{ recentMessage.senderName }}</v-list-item-title>
                            <v-list-item-subtitle> {{ recentMessage.date }}<br>{{ recentMessage.content }}
                            </v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
            </v-list>
        </v-navigation-drawer>
        <v-navigation-drawer
            app
            width="300"
            clipped
        >
            <v-list rounded>
                <v-subheader>COLLEAGUES</v-subheader>
                <v-list-item-group
                    mandatory
                    color="primary"
                    v-model="selectedItem">
                    <v-list-item
                        v-for="(friend, i) in friends"
                        :key="i"
                    >
                        <v-list-item-avatar>
                            <v-img :src="friend.img"></v-img>
                        </v-list-item-avatar>
                        <v-list-item-content>
                            <v-list-item-title>
                                {{ friend.firstName}} {{friend.secondName}} {{friend.lastName}}
                            </v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list-item-group>
            </v-list>

        </v-navigation-drawer>
        <v-main>
            <v-container v-if="isChatOpen">
                <v-toolbar class="mb-5" flat>
                    <v-spacer></v-spacer>
                    <v-badge bordered bottom dot offset-x="9" offset-y="13" color="green">
                        <v-avatar>
                            <v-img :src="friends[selectedItem].img" v-if="friends[selectedItem].img"></v-img>
                             <span v-else class="white--text text-h5">36</span>
                        </v-avatar>
                    </v-badge>
                    <v-toolbar-title class="title ml-3">
                        {{ `${friends[selectedItem].firstName} ${friends[selectedItem].secondName} ${friends[selectedItem].lastName}` }}
                    </v-toolbar-title>
                </v-toolbar>
                <v-divider></v-divider>
                <v-responsive max-height="780px" class="overflow">
                    <v-col cols="12">
                        <v-toolbar flat v-for="(message, i) in messages" :key="i" class="mt-10">
                            <v-spacer></v-spacer>
                            <transition appear name="chat-transition" v-on:after-enter="afterEnter">
                                <v-card
                                    :color="message.color"
                                    dark
                                    class="rounded-lg"
                                    max-width="300px">
                                    <v-list-item three-line>
                                        <v-list-item-content>
                                            <div class="mb-4">
                                                {{ message.content }}
                                            </div>
                                            <v-list-item-subtitle>{{ message.date }}
                                            </v-list-item-subtitle>
                                        </v-list-item-content>
                                    </v-list-item>
                                </v-card>
                            </transition>
                            <v-badge bordered bottom dot offset-x="9" offset-y="13" color="green">
                                <v-avatar class="ml-3">
                                    <v-img :src="message.senderImg"></v-img>
                                </v-avatar>
                            </v-badge>
                        </v-toolbar>
                    </v-col>
                    <div ref="scrollable"></div>
                </v-responsive>
            </v-container>
        </v-main>
        <v-footer
            app
            color="transparent"
            height="72"
            inset
        >
            <v-row>
                <v-text-field
                    v-model="typedMessage"
                    rounded
                    dense
                    filled
                    placeholder="Send message"
                    @keyup.enter="sendMessage"
                ></v-text-field>
                <v-btn text color="primary" :disabled="!typedMessage" @click="sendMessage">
                    <v-icon>
                        mdi-send
                    </v-icon>
                </v-btn>
            </v-row>
        </v-footer>
    </v-app>
</template>

<script>

import {tokenAxios} from "@/axios";
import {format} from "date-fns"
import differenceInHours from 'date-fns/differenceInHours'
import differenceInMinutes from 'date-fns/differenceInMinutes'
import differenceInSeconds from 'date-fns/differenceInSeconds'


export default {
    name: "Messenger",
    data() {
        return {
            logo: `${process.env.VUE_APP_BASE_URL}/images/static/logo_transparent1.png`,
            isChatOpen: false,
            typedMessage: '',
            selectedItem: 0,
            friends: [],
            img: localStorage.getItem('img'),
            firstName: localStorage.getItem('firstName'),
            messages: [],
            recentMessages: [],
            chatIndex: null,
            isUserOnline: false
        }
    },

    watch: {
        selectedItem(val) {
            this.chatIndex && this.$store.state.stompClient.unsubscribe('chat')
            this.setSubscription(localStorage.getItem('id'), this.friends[this.selectedItem].id)
            this.openChat(this.friends[val])
        },
    },


    created() {

        tokenAxios.get('users/except-for/' + localStorage.getItem('id'))
            .then((response) => {
                this.friends = response.data
                this.getRecentMessages()
                this.setSubscription(localStorage.getItem('id'), this.friends[this.selectedItem].id)
                this.isChatOpen = true
                this.openChat(this.friends[this.selectedItem])
            })


    },

    methods: {

        getRecentMessages(){
            tokenAxios.get('users/' + localStorage.getItem('id') + '/messages')
                .then(response => {
                    response.data.forEach((message) => {
                        let friend = this.friends.find(friend => friend.id === message.senderId)
                        console.log(friend)
                        this.recentMessages.push({
                            content: message.content,
                            date: message.date.substring(0, 10) + ' ' + message.date.substring(11),
                            senderName: `${friend.firstName} ${friend.secondName} ${friend.lastName}`,
                            senderImg: friend.img
                        })
                    })
                })
        },

        goBack(){
            this.$store.state.stompClient.unsubscribe('chat')
            this.$emit('messengerClosed')
        },

        afterEnter(){
            this.$refs['scrollable'].scrollIntoView({ behavior: 'smooth' })
        },

        setSubscription(myId, friendId) {
            this.chatIndex = parseInt(myId) + parseInt(friendId)
            this.$store.state.stompClient.subscribe('/topic/' + this.chatIndex, (message) => {
                if (!message.body) {
                    return
                }
                let newMessage = JSON.parse(message.body)
                let color, image
                if(newMessage.receiverId === parseInt(localStorage.getItem('id'))){
                    color = 'primary'
                    image = this.friends[this.selectedItem].img
                }
                else{
                    color = 'pink'
                    image = localStorage.getItem('img')
                }
                this.messages.push({
                    content: newMessage.content,
                    date: newMessage.date.substring(0, 10) + ' ' + newMessage.date.substring(11),
                    senderImg: image,
                    color: color
                })

            }, {id: 'chat'});

        },


        sendMessage() {
            let newMessage = {
                content: this.typedMessage,
                senderId: localStorage.getItem('id'),
                receiverId: this.friends[this.selectedItem].id,
                date: format(new Date(), 'yyyy-MM-dd HH:mm')
            }
            let sum = parseInt(newMessage.senderId) + parseInt(newMessage.receiverId)
            this.$store.state.stompClient.send('/app/hello/' + sum, JSON.stringify(newMessage))
            this.typedMessage = ''

        },

        setDifference(date) {
            diff = differenceInHours(new Date(), date)
            if (diff !== 0) {
                if (diff === 1) {
                    return diff + ' hour ago'
                }
                return diff + ' hours ago'
            }
            diff = differenceInMinutes(new Date(), date)
            if (diff !== 0) {
                if (diff === 1) {
                    return diff + ' minute ago'
                }
                return diff + ' minutes ago'
            }
            let diff = differenceInSeconds(new Date(), date)
            if (diff !== 0) {
                return diff + ' seconds ago'
            }
            return format(date, 'yyyy-MM-dd HH:mm')

        },


        openChat(friend) {
            this.messages = []
            tokenAxios.get('messages', {
                params: {
                    senderId: friend.id,
                    receiverId: localStorage.getItem('id')
                }
            }).then((response) => {
                let senderImg
                let color
                response.data.forEach(message => {
                    if (friend.id === message.senderId) {
                        senderImg = friend.img
                        color = 'primary'
                    } else {
                        senderImg = this.img
                        color = 'pink'
                    }
                    let date = this.setDifference(new Date(message.date))
                    this.messages.push({
                        content: message.content,
                        date: date,
                        senderImg: senderImg,
                        color: color
                    })

                })
            })

        }
    }

}
</script>

<style scoped>

.overflow{
    overflow-y: auto;
}

.chat-transition-enter-active,
.chat-transition-leave-active {
    transition: opacity 0.7s ease;
}

.chat-transition-enter, .chat-transition-leave-to {
    opacity: 0;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 1s ease;
}
</style>