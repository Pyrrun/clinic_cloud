<template>
    <v-navigation-drawer
        main
        absolute
        temporary
        v-model="drawer"
    >
        <v-list>
            <v-list-item>
                <v-list-item-avatar>
                    <v-img :src="img"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                    <v-list-item-title class="font-weight-bold">{{ name }}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
        <v-divider></v-divider>
        <v-list  rounded>
                <v-list-item
                    v-for="(item, i) in items"
                    :key="i"
                    @click="redirect(i)"
                    >
                    <v-list-item-icon>
                        <v-icon color="primary">{{item.icon}}</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>{{item.title}}</v-list-item-title>
                </v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
export default {
    name: "Navigation",
    props: {
        navigationOpened: Boolean
    },
    data() {
        return {
            img: localStorage.getItem('img'),
            cardIndex: null,
            name: localStorage.getItem('firstName'),
            drawer: this.navigationOpened,
            items: [
                {icon: 'mdi-home', title: 'Home'},
                // {icon: 'mdi-account', title: 'My account'},
                {icon: 'mdi-calendar', title: 'New visit'},
                {icon: 'mdi-account-heart', title: 'Patients'},
                {icon: 'mdi-account-check-outline', title: 'New patient'},
            ]

        }
    },
    watch: {
        navigationOpened(val) {
            this.drawer = val
        },
        drawer(val){
            if(!val){
                this.$emit('navigationClosed')
            }
        }
    },

    created(){
        if(localStorage.getItem('imageName') === null ){
            this.imgSrc = `${process.env.VUE_APP_BASE_URL}/images/static/avatar.png`
        }
    },


    methods: {
        redirect(index) {
            this.drawer = false
            this.$emit('navigationChosen', index)
            this.$vuetify.goTo(this.$store.state.target, this.$store.state.options)

        }
    },


}
</script>

<style scoped>
.v-navigation-drawer {
    will-change: initial;
}
</style>