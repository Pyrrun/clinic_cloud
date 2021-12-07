<template>
    <v-card height="880"
    >
        <v-card-title class="text-h2 text-sm-h2 text-md-h2 text-lg-h2">Activities</v-card-title>
        <v-card-subtitle>Manage your visits, patients and schedule</v-card-subtitle>
        <v-divider class="mx-4"></v-divider>
        <v-container fluid>
            <v-row dense>
                <v-col
                    v-for="card in cards"
                    :key="card.title"
                    cols="6"
                >
                    <v-hover
                        v-slot="{hover}">
                        <v-card
                            :elevation="hover ? 16 : 2"
                            @click="redirect(card.index)"
                        >
                            <v-card-title v-text="card.title"></v-card-title>
                            <v-card-subtitle v-text="card.subtitle"></v-card-subtitle>
                            <v-img
                                :src="card.src"
                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                height="280px"
                            >
                                <v-expand-transition>
                                <div v-if="hover" class="d-flex transition-fast-in-fast-out transparent align-center justify-center darken-4 v-card--reveal display-3 black--text " style="height: 100%;">
                                    <v-icon size="80px">
                                        {{ card.icon }}
                                    </v-icon>
                                </div>
                                </v-expand-transition>
                            </v-img>
                        </v-card>
                    </v-hover>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
</template>

<script>
export default {
    name: "Activities",
    data() {
        return {
            name: localStorage.getItem('firstName') || '',
            cardIndex: null,
            cards: [
                {
                    title: 'My visits',
                    subtitle: 'Browse through your visits',
                    src: `${process.env.VUE_APP_BASE_URL}/images/static/visit.jpg`,
                    index: 1,
                    icon: 'mdi-calendar'
                },
                {
                    title: 'My patients',
                    subtitle: 'Manage your patients',
                    src: `${process.env.VUE_APP_BASE_URL}/images/static/doctor-patient.jpg`,
                    index: 3,
                    icon: 'mdi-account-heart'
                },
                {
                    title: 'My schedule',
                    subtitle: 'Manage your working hours',
                    src: `${process.env.VUE_APP_BASE_URL}/images/static/schedule.jpg`,
                    index: 2,
                    icon: 'mdi-calendar-heart'
                },
                {
                    title: 'Interact',
                    subtitle: 'Contact your colleagues via chat',
                    src: `${process.env.VUE_APP_BASE_URL}/images/static/forum.png`,
                    index: 6,
                    icon: 'mdi-forum'
                },
            ],
        }
    },
    methods: {
        redirect(index) {
            this.cardIndex = index
            this.$emit('cardChosen', index)

        },
    }
}
</script>

<style scoped>

</style>