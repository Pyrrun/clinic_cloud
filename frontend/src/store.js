import Vue from 'vue';
import Vuex from 'vuex'
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";


Vue.use(Vuex)


export const store = new Vuex.Store({
    state:{
        token: '' | localStorage.getItem('token'),
        date: '',
        patient: {},
        doctorsAchieved: false,
        patientSelected: false,
        webSocketConnectionEstablished: false,
        cardIndex: null,
        stompClient: null,
        webSocket: null,
        scrollDuration: 300,
        scrollOffset: 0,
        scrollEasing: 'easeInOutCubic',
        target: 650,
        options: {duration: 300, offset: 0, easing: 'easeInOutCubic'}

    },
    mutations: {
        initializeWebSocketConnection(state){
            state.webSocket = new SockJS(`${process.env.VUE_APP_BASE_URL}/doctor-panel`)
            state.stompClient = Stomp.over(state.webSocket)
            state.stompClient.connect({}, (frame) => {
                console.log('Connected: ' + frame);
                state.webSocketConnectionEstablished = true
            })
        },


    },




});
