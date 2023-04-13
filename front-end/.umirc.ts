import { defineConfig } from 'umi';
import {
  SmileOutlined,
  FileAddOutlined,
  FileDoneOutlined,
  CrownOutlined,
  SolutionOutlined,
  TabletOutlined,
  AntDesignOutlined,
} from '@ant-design/icons';
export default defineConfig({
  proxy: {
    '/api': {
      target: 'http://82.156.252.198:8080',
      changeOrigin: true,
    },
    '/files': {
      target: 'http://82.156.252.198:8080',
      changeOrigin: true,
    },
  },
  nodeModulesTransform: {
    type: 'none',
  },
  layout: {
    name: '在线合同签署',
    logo: '/files?file=0.png',
    locale: false,
  },
  routes: [
    {
      path: './test',
      layout: false,
      component: '@/pages/Test/Test',
    },

    {
      path: './',
      layout: false,
      component: '@/pages/Home',
    },
    {
      path: './Login',
      layout: false,
      component: '@/pages/Login/Login',
    },
    {
      path: './welcome',
      name: '欢迎',
      icon: 'SmileOutlined',
      component: '@/pages/Welcome/Welcome',
    },
    {
      path: './home',
      layout: false,
      component: '@/pages/Home/index',
    },
    {
      path: '/contract/new',
      name: '创建合同',
      icon: 'FileAddOutlined',
      component: '@/pages/NewContract/New',
    },
    {
      path: '/contract/sign',
      name: '签署合同',
      icon: 'FileDoneOutlined',
      component: '@/pages/SignContract/Sign',
    },
    {
      path: '/contract/dashboard',
      name: '我的合同',
      icon: 'SolutionOutlined',
      component: '@/pages/DashBoard/DashBoard',
    },
    {
      name: '个人信息',
      path: '/userInfo',
      icon: 'SolutionOutlined',
      component: '@/pages/User/User',
    },
    {
      name: '个人信息设置',
      path: '/usersetup',
      icon: 'SolutionOutlined',
      component: '@/pages/UserSetup/UserSetup',
    },
  ],
  fastRefresh: {},
});
