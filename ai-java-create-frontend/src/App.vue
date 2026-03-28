<script setup lang="ts">
import BasicLayout from '@/layouts/BasicLayout.vue'

import { hello } from '@/api/testController.ts'

hello().then((res) => {
  console.log(res)
})
</script>

<template>
  <BasicLayout />
</template>

<style>
</style>
