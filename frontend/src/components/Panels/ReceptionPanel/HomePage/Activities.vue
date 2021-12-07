<template>
    <v-card min-height="880"
    >
        <v-card-title class="text-h2 text-sm-h2 text-md-h2 text-lg-h2">Activities</v-card-title>
        <v-card-subtitle>Manage your patients and reserve new visits conveniently</v-card-subtitle>
        <v-divider class="mx-4"></v-divider>
        <v-container fluid>
            <v-row dense>
                <v-col
                    v-for="card in cards"
                    :key="card.title"
                    :cols="card.flex"
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
                                    <div v-if="hover" class="d-flex transition-fast-in-fast-out transparent align-center justify-center darken-4 v-card--reveal display-3  " style="height: 100%;">
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
                    title: 'Patients',
                    subtitle: 'Browse through patients and their visits',
                    src: `${process.env.VUE_APP_BASE_URL}/images/static/Patient-holding-doctors-hand.jpg`,
                    flex: 6,
                    index: 2,
                    icon: 'mdi-account-heart'
                },
                {
                    title: 'New visit',
                    subtitle: 'Register a new visit for patient',
                    src: `${process.env.VUE_APP_BASE_URL}/images/static/visit.jpg`,
                    flex: 6,
                    index: 1,
                    icon: 'mdi-calendar'
                },
                {
                    title: 'Interact',
                    subtitle: 'Contact your colleagues via chat',
                    src: `${process.env.VUE_APP_BASE_URL}/images/static/forum.png`,
                    flex: 6,
                    index: 5,
                    icon: 'mdi-forum'
                },
                {
                    title: 'Register new patient',
                    subtitle: 'Add new person as patient',
                    src: `https://j-elita.org.pl/wp-content/uploads/2021/03/lekarz-i-pacjent.jpg`,
                    flex: 6,
                    index: 3,
                    icon: 'mdi-account-check-outline'
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