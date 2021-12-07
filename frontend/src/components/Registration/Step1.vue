<template>
  <v-card-text>
    <v-row class="text-center">
      <v-col class="mb-4">
        <h2 class="display-1 font-weight-bold mb-3">Step 1</h2>
        <h3 class="headline font-weight-thin mb-3">
          <i>Login credentials </i>
        </h3>
        <transition name="fade">
          <p style="color: red" v-if="errorPopped">{{ error }}</p>
        </transition>
      </v-col>
    </v-row>
    <v-form ref="form" v-model="valid" class="login">
      <v-row justify="center">
        <v-col cols="10" lg="7" xs="11" sm="9">
          <v-text-field
            type="email"
            :rules="emailRules"
            clearable
            label="E-mail"
            error-count="1"
            required
            v-model="email"
          ></v-text-field>
          <v-text-field
            type="email"
            autocomplete="off"
            :rules="emailRules"
            clearable
            label="Confirm e-mail"
            error-count="1"
            required
            v-model="confirmEmail"
          ></v-text-field>
          <v-text-field
            :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
            :type="showPassword ? 'text' : 'password'"
            :rules="passwordRules"
            clearable
            label="Password"
            error-count="5"
            required
            v-model="password"
            @click:append="showPassword = !showPassword"

          ></v-text-field>
          <v-text-field
            :append-icon="showPasswordConfirm ? 'mdi-eye' : 'mdi-eye-off'"
            :type="showPasswordConfirm ? 'text' : 'password'"
            @click:append="showPasswordConfirm = !showPasswordConfirm"
            clearable
            label="Confirm password"
            :rules="[(v) => !!v || 'Please confirm your password.']"
            error-count="5"
            required
            v-model="confirmPassword"
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="7">
          <transition name="fade">
            <v-btn
              color="primary"
              large
              @click="validateAndContinue"
              block
              :disabled="!buttonEnabled"
              medium
              >Submit
            </v-btn>
          </transition>
        </v-col>
      </v-row>
    </v-form>
  </v-card-text>
</template>

<script>
import axios from "@/axios";

export default {
  name: "Step1",

  data() {
    return {
      valid: false,
      showPassword: false,
      showPasswordConfirm: false,
      passwordRules: [
        (v) => !!v || "Please enter your password.",
        (v) =>
          (v && v.length >= 8) ||
          "Your password should contain at least 8 digits.",
        (v) =>
          /(?=.*[A-Z])/.test(v) ||
          "Your password should contain at least one capital letter.",
        (v) =>
          /(?=.*\d)/.test(v) ||
          "Your password should contain at least one number.",
        (v) =>
          /([!@$%])/.test(v) ||
          "Your password should contain at least one special sign (!@$%).",
      ],
      emailRules: [
        (v) => !!v || "Please enter your e-mail.",
        (v) =>
          /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/.test(
            v
          ) || "Correct e-mail example: example@example.com",
      ],
      buttonEnabled: false,
      errorPopped: false,
      password: "",
      confirmPassword: "",
      confirmEmail: "",
      email: "",
      error: "",
      user: [],
    };
  },
  watch: {
    valid() {
      console.log(this.valid);
      setTimeout(this.showButton, 200);
      this.$emit("validCheck", this.valid);
    },
  },
  methods: {
    showButton() {
      this.buttonEnabled = this.valid;
    },

    validateAndContinue() {
      this.errorPopped = false;
      if (this.password !== this.confirmPassword) {
        this.error = "Passwords are different";
        this.errorPopped = true;
        return;
      }
      if (this.email !== this.confirmEmail) {
        this.error = "E-mails are different";
        this.errorPopped = true;
        return;
      }
      axios
        .get("users/email", {
          params: {
            email: this.email,
          },
        })
        .then((response) => {
          console.log(response.data);
          console.log(this.email);
          this.$emit("firstStepComplete", {
            email: this.email,
            password: this.password,
          });
          this.$toast.success("Step 1 completed successfully.");
        })
        .catch(() => {
          this.error = "This e-mail is already used by another user";
          this.errorPopped = true;
        });
    },
  },
};
</script>

<style scoped>
</style>