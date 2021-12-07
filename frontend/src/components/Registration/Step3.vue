<template>
  <v-container>
    <v-row class="text-center">
      <v-col class="mb-4">
        <h2 class="display-1 font-weight-bold mb-3">Step 3</h2>
        <h3 class="headline font-weight-thin mb-3">
          <i>Profile image</i>
        </h3>
        <h4>Drag and drop files to upload.</h4>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col xs="11" lg="9" align="center">
        <v-card
          ref="card"
          
          :elevation="elevation"
          shaped
          @dragover.prevent="dragover"
          @dragleave.prevent="dragend"
          @dragexit.prevent="dragend"
          @drop="drop"
        >
          <v-container fluid>
            <v-img :src="previewImage" width="100%" />
            <v-btn
              class="ma-2"
              color="indigo"
              dark
              @click="browse"
              type="input"
              :loading="isLoading"
            >
            Upload
              <v-icon right dark> large mdi-cloud-upload </v-icon>
            </v-btn>
            <input
              type="file"
              style="display: none"
              ref="img"
              accept="image/*"
              @change="onBrowsing"
            />
            <v-card-actions>
              <v-col>
                <v-row justify="space-between">
                  <v-btn
                    :disabled="imageLoaded === false"
                    color="primary"
                    text
                    @click="clearImage"
                  >
                    Clear
                  </v-btn>
                  <v-btn
                    color="primary"
                    :disabled="imageLoaded === false"
                    text
                    :loading="isSubmitLoading"
                    @click="submitImage"
                    >Submit
                  </v-btn>
                </v-row>
              </v-col>
            </v-card-actions>
          </v-container>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Step3",
  props: {
    continueClicked: Boolean,
    registrationType: String,
  },
  data() {
    return {
      isLoading: false,
      isSubmitLoading: false,
      selectedFile: null,
      previewImage: `${process.env.VUE_APP_BASE_URL}/images/static/noPreview.jpg`,
      imageLoaded: false,
      buttonEnabled: false,
      img: null,
      elevation: 5,
      formData: null,
    };
  },
  created() {
    console.log(this.registrationType);
  },

  watch: {
    continueClicked() {
      this.nextStep();
    },
  },
  methods: {
    browse() {
      this.isLoading = true;
      window.addEventListener(
        "focus",
        () => {
          this.isLoading = false;
        },
        { once: true }
      );
      this.$refs.img.click();
    },

    onBrowsing(e) {
      this.selectedFile = e.target.files[0];
      if (this.selectedFile === undefined) {
        return;
      }
      this.readImage(this.selectedFile);
    },

    dragover() {
      this.elevation = 12;
    },
    dragend() {
      this.elevation = 5;
    },
    drop(event) {
      event.preventDefault();
      this.readImage(event.dataTransfer.files[0]);
    },

    readImage(val) {
      const fr = new FileReader();
      fr.readAsDataURL(val);
      fr.onload = (e) => {
        this.previewImage = e.target.result;
        this.imageLoaded = true;
        this.elevation = 5;
      };
    },

    nextStep() {
      this.img || this.$toast.warning('Your image has not been submitted. To upload, please press the submit button.', {duration: 4000})
      this.$emit("thirdStepComplete", this.img);
    },

    submitImage() {
      this.isSubmitLoading = true;
      setTimeout(() => {
        this.img = this.selectedFile;
        this.isSubmitLoading = false;
        this.$toast.success("Image uploaded successfully.");
      }, 1000);
    },
    clearImage() {
      this.previewImage = `${process.env.VUE_APP_BASE_URL}/images/static/noPreview.jpg`;
      this.imageLoaded = false;
      this.selectedFile = null;
      this.img = null;
      this.$refs.image.value = null;
    },
  },
};
</script>

<style scoped>
</style>