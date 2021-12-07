<template>
    <v-app>
        <transition name="slide" mode="out-in" appear>
        <router-view :key="$route.name"></router-view>
        </transition>
    </v-app>

</template>

<script>


export default {
    name: 'App',
    data() {
        return {
            transitionName: ''
        }
    },

    // created() {
    //     this.setInitialTransition(this.$route)
    // },
    //
    // watch: {
    //     '$route'(to,from) {
    //         this.setTransition(to, from)
    //     },
    //
    // },
    // methods: {
    //     setInitialTransition(route) {
    //         if (route.name === 'panelSelector') {
    //             this.transitionName = 'fade'
    //         }
    //         if (route.name === 'login') {
    //             this.transitionName = 'slide'
    //         }
    //
    //     },
    //     setTransition(to, from) {
    //         if (from.name === 'panelSelector') {
    //             this.transitionName = 'slide'
    //         }
    //         if (from.name === 'login') {
    //             this.transitionName = 'fade'
    //         }
    //
    //     }


   // }
}
</script>

<style>
</style>
