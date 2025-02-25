<template>
  <div>
    <el-alert title="图床配置及用法请查看：https://github.com/Naccl/PictureHosting" type="warning" show-icon
              v-if="hintShow"></el-alert>
    <el-card>
      <div slot="header">
        <span>GitHub配置</span>
      </div>
      <el-row>
        <el-col>
          <el-input placeholder="请输入token进行初始化" v-model="githubToken" :clearable="true"
                    @keyup.native.enter="searchGithubUser" style="min-width: 500px">
            <el-button slot="append" icon="el-icon-search" @click="searchGithubUser">查询</el-button>
          </el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <span class="middle">当前用户：</span>
          <el-avatar :size="50" :src="githubUserInfo.avatar_url">User</el-avatar>
          <span class="middle">{{ githubUserInfo.login }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-button type="primary" size="medium" icon="el-icon-check" :disabled="!isGithubSave"
                     @click="saveGithub(true)">保存配置
          </el-button>
          <el-button type="info" size="medium" icon="el-icon-close" @click="saveGithub(false)">清除配置</el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card>
      <div slot="header">
        <span>腾讯云存储配置</span>
      </div>
      <el-form :model="txyunConfig" label-width="100px">
        <el-form-item label="secret-id">
          <el-input v-model="txyunConfig.secretId"></el-input>
        </el-form-item>
        <el-form-item label="secret-key">
          <el-input v-model="txyunConfig.secretKey"></el-input>
        </el-form-item>
        <el-form-item label="存储空间名">
          <el-input v-model="txyunConfig.bucketName"></el-input>
        </el-form-item>
        <el-form-item label="地域">
          <el-input v-model="txyunConfig.region"></el-input>
        </el-form-item>
        <el-form-item label="CDN访问域名">
          <el-input v-model="txyunConfig.domain"></el-input>
        </el-form-item>
        <el-button type="primary" size="medium" icon="el-icon-check" :disabled="!isTxyunSave" @click="saveTxyun(true)">
          保存配置
        </el-button>
        <el-button type="info" size="medium" icon="el-icon-close" @click="saveTxyun(false)">清除配置</el-button>
      </el-form>
    </el-card>
    <el-card>
      <div slot="header">
        <span>阿里云存储配置</span>
      </div>
      <el-form :model="aliyunConfig" label-width="100px">
        <el-form-item label="endpoint">
          <el-input v-model="aliyunConfig.endpoint"></el-input>
        </el-form-item>
        <el-form-item label="bucket-name">
          <el-input v-model="aliyunConfig.bucketName"></el-input>
        </el-form-item>
        <el-form-item label="access-key-id">
          <el-input v-model="aliyunConfig.accessKeyId"></el-input>
        </el-form-item>
        <el-form-item label="secret-access-key">
          <el-input v-model="aliyunConfig.secretAccessKey"></el-input>
        </el-form-item>
        <el-button type="primary" size="medium" icon="el-icon-check" :disabled="!isAliyunSave"
                   @click="saveAliyun(true)">保存配置
        </el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {getUserInfo} from "@/api/github";

export default {
  name: "Setting",
  data() {
    return {
      githubToken: '',
      githubUserInfo: {
        login: '未配置'
      },
      isGithubSave: false,
      hintShow: false,
      txyunConfig: {
        secretId: '',
        secretKey: '',
        bucketName: '',
        region: '',
        domain: ''
      },
      aliyunConfig: {
        endpoint: '',
        bucketName: '',
        accessKeyId: '',
        secretAccessKey: ''
      }
    }
  },
  computed: {
    isTxyunSave() {
      return this.txyunConfig.secretId && this.txyunConfig.secretKey && this.txyunConfig.bucketName && this.txyunConfig.region && this.txyunConfig.domain
    },
    isAliyunSave() {
      return this.aliyunConfig.endpoint && this.aliyunConfig.bucketName && this.aliyunConfig.accessKeyId && this.aliyunConfig.secretAccessKey
    }
  },
  created() {
    this.githubToken = localStorage.getItem("githubToken")
    const githubUserInfo = localStorage.getItem('githubUserInfo')
    if (this.githubToken && githubUserInfo) {
      this.githubUserInfo = JSON.parse(githubUserInfo)
      this.isGithubSave = true
    } else {
      this.githubUserInfo = {login: '未配置'}
    }

    const txyunConfig = localStorage.getItem('txyunConfig')
    if (txyunConfig) {
      this.txyunConfig = JSON.parse(txyunConfig)
    }

    const aliyunConfig = localStorage.getItem('aliyunConfig')
    if (aliyunConfig) {
      this.aliyunConfig = JSON.parse(aliyunConfig)
    }


    const userJson = window.localStorage.getItem('user') || '{}'
    const user = JSON.parse(userJson)
    if (userJson !== '{}' && user.role !== 'ROLE_admin') {
      //对于访客模式，增加个提示
      this.hintShow = true
    }
  }
  ,
  methods: {
    // 获取用户信息
    searchGithubUser() {
      getUserInfo(this.githubToken).then(res => {
        this.githubUserInfo = res
        this.isGithubSave = true
      })
    }
    ,
    saveGithub(save) {
      if (save) {
        localStorage.setItem('githubToken', this.githubToken)
        localStorage.setItem('githubUserInfo', JSON.stringify(this.githubUserInfo))
        this.msgSuccess('保存成功')
      } else {
        localStorage.removeItem('githubToken')
        localStorage.removeItem('githubUserInfo')
        this.msgSuccess('清除成功')
      }
    }
    ,
    saveTxyun(save) {
      if (save) {
        localStorage.setItem('txyunConfig', JSON.stringify(this.txyunConfig))
        this.msgSuccess('保存成功')
      } else {
        localStorage.removeItem('txyunConfig')
        this.msgSuccess('清除成功')
      }
    },
    saveAliyun(save) {
      if (save) {
        localStorage.setItem('aliyunConfig', JSON.stringify(this.aliyunConfig))
        this.msgSuccess('保存成功')
      } else {
        localStorage.removeItem('aliyunConfig')
        this.msgSuccess('清楚成功')
      }
    }
  }
  ,
}
</script>

<style scoped>
.el-alert + .el-row, .el-row + .el-row {
  margin-top: 20px;
}

.el-avatar {
  vertical-align: middle;
  margin-right: 15px;
}

.middle {
  vertical-align: middle;
}

.el-card {
  width: 50%;
}

.el-card + .el-card {
  margin-top: 20px;
}
</style>
