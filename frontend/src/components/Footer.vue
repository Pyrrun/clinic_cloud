<template>
    <v-footer class="mt-4"
              padless
              main
    >
        <v-row justify="center">
            <v-card
                width="100%"
                flat
                tile
                class="indigo lighten-1 white--text text-center"
            >
                <v-card-text>
                    <v-btn
                        v-for="icon in icons"
                        :key="icon"
                        class="mx-4 white--text"
                        icon
                    >
                        <v-icon size="24px">
                            {{ icon }}
                        </v-icon>
                    </v-btn>
                </v-card-text>
                <v-card-text class="white--text pt-0">
                    This is a simple medical panel created in educational purposes, designed with Vuetify.
                </v-card-text>
                <v-divider></v-divider>
                <v-card-text class="white--text">
                    {{ new Date().getFullYear() }} — <strong>Krzysztof Wyszyński</strong>
                </v-card-text>
            </v-card>
        </v-row>
    </v-footer>
</template>

<script>
export default {
    name: "Footer",
    data(){
        return{
            icons: [
                'mdi-facebook',
                'mdi-twitter',
                'mdi-linkedin',
                'mdi-instagram',
            ],
        }
    }
}
</script>

<style scoped>

</style>