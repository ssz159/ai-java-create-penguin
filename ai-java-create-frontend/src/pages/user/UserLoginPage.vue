<template>
  <div id="userLoginPage">
    <h2 class="title">企鹅 AI 应用生成 - 用户登录</h2>
    <div class="desc">不写一行代码，生成完整应用</div>
    <a-form :model="formState" name="basic" autocomplete="off" @finish="handleSubmit">
      <a-form-item name="userAccount" :rules="[{ required: true, message: '请输入账号' }]">
        <a-input v-model:value="formState.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item
        name="userPassword"
        :rules="[
          { required: true, message: '请输入密码' },
          { min: 8, message: '密码不能小于 8 位' },
        ]"
      >
        <a-input-password v-model:value="formState.userPassword" placeholder="请输入密码" />
      </a-form-item>
      <div class="tips">
        没有账号？
        <RouterLink to="/user/register">去注册</RouterLink>
      </div>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%">登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router'
import { useLoginUserStore } from '@/stores/loginUser.ts'
import { userLogin } from '@/api/userController.ts'
import { message } from 'ant-design-vue'
const formState = reactive<API.UserLoginRequest>({
  userAccount: '',
  userPassword: '',
});

const router = useRouter()
const loginUserStore = useLoginUserStore()

/**
 * 提交表单
 * @param values
 */
const handleSubmit = async (values: any) => {
  const res = await userLogin(values)
  // 登录成功，把登录态保存到全局状态中
  if (res.data.code === 0 && res.data.data) {
    await loginUserStore.fetchLoginUser()
    message.success('登录成功')
    router.push({
      path: '/',
      replace: true,
    })
  } else {
    message.error('登录失败，' + res.data.message)
  }
}

</script>

<style scoped>#userLoginPage {
  max-width: 400px;
  margin: 100px auto;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  background: #fff;
}

.title {
  text-align: center;
  margin-bottom: 8px;
  font-size: 24px;
  color: #1f1f1f;
}

.desc {
  text-align: center;
  margin-bottom: 32px;
  color: rgba(0, 0, 0, 0.45);
  font-size: 14px;
}

.tips {
  margin-bottom: 16px;
  text-align: right;
  font-size: 13px;
}

.tips a {
  color: #1890ff;
  text-decoration: none;
}

.tips a:hover {
  text-decoration: underline;
}
</style>
