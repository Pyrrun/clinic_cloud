<template>
    <v-card>
        <v-card-title>
            New notice
        </v-card-title>
        <v-card-subtitle>
            Important message to your colleagues
        </v-card-subtitle>
        <v-card-text>
           
            <v-form v-model="valid">
                <v-text-field
                    v-model="title"
                    label="Title"
                    @keypress="isMaxLength($event)"
                    counter
                    maxlength="30"
                    :rules="[() => !!title || 'This field is required']"
                >
                </v-text-field>
                <v-textarea
                    rows="8"
                    v-model="content"
                    solo
                    label="Content"
                    :rules="[() => !!content || 'This field is required']"
                >
                </v-textarea>
            </v-form>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn text color="primary" @click="saveNotice" :disabled="!valid">Submit</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
//import {tokenAxios} from "@/axios";
import {format} from "date-fns"

export default {
    name: "NewNotice",
    data() {
        return {
            content: '',
            title: '',
            valid: false,
        }
    },

    created() {
        console.log(format(new Date(), 'yyyy-MM-dd HH:mm:ss'))
    },
    methods: {
        isMaxLength(event) {
            if (this.title.length > 30) {
                event.preventDefault()
            }
        },
        saveNotice() {
            let requestNotice = {
                title: this.title,
                content: this.content,
                doctorId: localStorage.getItem('id'),
                date: format(new Date(), 'yyyy-MM-dd HH:mm:ss')
            }
            this.$emit('newNoticeSaved', requestNotice)
        }
    }
}
</script>

<style scoped>

</style>