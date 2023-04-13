import React from 'react';
export const Nav00DataSource = {
  wrapper: { className: 'header0 home-page-wrapper' },
  page: { className: 'home-page' },
  logo: {
    className: 'header0-logo',
    children: '/files?file=0.png',
  },
  Menu: {
    className: 'header0-menu',
    children: [
      {
        name: 'item0',
        className: 'header0-item',
        children: {
          href: '#',
          children: [
            {
              children: '导航一',
              name: 'text',
              className: 'kp101d5n9ag-editor_css',
            },
          ],
        },
        subItem: [
          {
            name: 'sub0',
            className: 'item-sub',
            children: {
              className: 'item-sub-item',
              children: [
                {
                  name: 'image0',
                  className: 'item-image',
                  children:
                    'https://gw.alipayobjects.com/zos/rmsportal/ruHbkzzMKShUpDYMEmHM.svg',
                },
                {
                  name: 'title',
                  className: 'item-title',
                  children: 'Ant Design',
                },
                {
                  name: 'content',
                  className: 'item-content',
                  children: '企业级 UI 设计体系',
                },
              ],
            },
          },
          {
            name: 'sub1',
            className: 'item-sub',
            children: {
              className: 'item-sub-item',
              children: [
                {
                  name: 'image0',
                  className: 'item-image',
                  children:
                    'https://gw.alipayobjects.com/zos/rmsportal/ruHbkzzMKShUpDYMEmHM.svg',
                },
                {
                  name: 'title',
                  className: 'item-title',
                  children: 'Ant Design',
                },
                {
                  name: 'content',
                  className: 'item-content',
                  children: '企业级 UI 设计体系',
                },
              ],
            },
          },
        ],
      },
      {
        name: 'item1',
        className: 'header0-item kp101s3pop-editor_css',
        children: {
          href: '#',
          children: [{ children: '导航二', name: 'text' }],
        },
      },
      {
        name: 'item2',
        className: 'header0-item kp101yqtcqh-editor_css',
        children: {
          href: '#',
          children: [{ children: '导航三', name: 'text' }],
        },
      },
      {
        name: 'item3',
        className: 'header0-item kp107h9i7am-editor_css',
        children: {
          href: '/login',
          children: [
            {
              children: (
                <span>
                  <span>
                    <p>登录</p>
                  </span>
                </span>
              ),
              name: 'text',
            },
          ],
        },
      },
    ],
  },
  mobileMenu: { className: 'header0-mobile-menu' },
};
export const Banner01DataSource = {
  wrapper: { className: 'banner0' },
  textWrapper: { className: 'banner0-text-wrapper' },
  title: {
    className: 'banner0-title',
    children: 'http://82.156.252.198:8080/files?file=1.png',
  },
  content: {
    className: 'banner0-content',
    children: (
      <span>
        <span>
          <span>
            <p>
              基于区块链的高效公平多方合同签<span>署平台</span>
            </p>
          </span>
        </span>
      </span>
    ),
  },
  button: { className: 'banner0-button', children: 'Learn More' },
};
export const Content00DataSource = {
  wrapper: { className: 'home-page-wrapper content0-wrapper' },
  page: { className: 'home-page content0' },
  OverPack: { playScale: 0.3, className: '' },
  titleWrapper: {
    className: 'title-wrapper',
    children: [
      {
        name: 'title',
        children: (
          <span>
            <p>产品特点</p>
          </span>
        ),
      },
    ],
  },
  childWrapper: {
    className: 'content0-block-wrapper',
    children: [
      {
        name: 'block0',
        className: 'content0-block',
        md: 8,
        xs: 24,
        children: {
          className: 'content0-block-item',
          children: [
            {
              name: 'image',
              className: 'content0-block-icon',
              children:
                'https://zos.alipayobjects.com/rmsportal/WBnVOjtIlGWbzyQivuyq.png',
            },
            {
              name: 'title',
              className: 'content0-block-title',
              children: (
                <span>
                  <p>安全签署</p>
                </span>
              ),
            },
            {
              name: 'content',
              children: (
                <span>
                  <p>
                    利用密码学技术，为客户生成专有数字签名，以此保证合同签署的安全性。
                  </p>
                </span>
              ),
            },
          ],
        },
      },
      {
        name: 'block1',
        className: 'content0-block',
        md: 8,
        xs: 24,
        children: {
          className: 'content0-block-item',
          children: [
            {
              name: 'image',
              className: 'content0-block-icon',
              children:
                'https://zos.alipayobjects.com/rmsportal/YPMsLQuCEXtuEkmXTTdk.png',
            },
            {
              name: 'title',
              className: 'content0-block-title',
              children: (
                <span>
                  <p>便捷签署</p>
                </span>
              ),
            },
            {
              name: 'content',
              children: (
                <span>
                  <p>
                    所有步骤均将在线完成，无需繁杂的步骤，足不出户，即可完成签署。
                  </p>
                </span>
              ),
            },
          ],
        },
      },
      {
        name: 'block2',
        className: 'content0-block',
        md: 8,
        xs: 24,
        children: {
          className: 'content0-block-item',
          children: [
            {
              name: 'image',
              className: 'content0-block-icon',
              children:
                'https://zos.alipayobjects.com/rmsportal/EkXWVvAaFJKCzhMmQYiX.png',
            },
            {
              name: 'title',
              className: 'content0-block-title',
              children: (
                <span>
                  <p>高效签署</p>
                </span>
              ),
            },
            {
              name: 'content',
              children: (
                <span>
                  <p>多方合同秒速签署，实时追踪合同状态，随时随地调用查询。</p>
                </span>
              ),
            },
          ],
        },
      },
    ],
  },
};
export const Feature10DataSource = {
  wrapper: { className: 'home-page-wrapper content1-wrapper' },
  OverPack: { className: 'home-page content1', playScale: 0.3 },
  imgWrapper: { className: 'content1-img', md: 10, xs: 24 },
  img: {
    children:
      'https://iconfont.alicdn.com/s/4853644c-7490-4a7a-b817-064e223a7d44_origin.svg',
  },
  textWrapper: { className: 'content1-text', md: 14, xs: 24 },
  title: {
    className: 'content1-title',
    children: (
      <span>
        <p>无证书聚合加密签名技术</p>
      </span>
    ),
  },
  content: {
    className: 'content1-content',
    children: (
      <span>
        <p>
          无证书聚合加密签名技术（CLAVES方案）将CLAS
          协议和VES协议结合起来，可以利用各签署方共享的公钥对普通签名进行加密，为参与签署的各方生成聚合数字签名，保证了合同签署的安全性，并且提高了签署过程的效率。
        </p>
      </span>
    ),
  },
};
export const Feature20DataSource = {
  wrapper: { className: 'home-page-wrapper content2-wrapper' },
  OverPack: { className: 'home-page content2', playScale: 0.3 },
  imgWrapper: { className: 'content2-img', md: 10, xs: 24 },
  img: {
    children:
      'https://iconfont.alicdn.com/s/b0626384-770c-47e1-b049-bed0e9fc76b1_origin.svg',
  },
  textWrapper: { className: 'content2-text', md: 14, xs: 24 },
  title: {
    className: 'content2-title',
    children: (
      <span>
        <p>
          基于区块链的COR方案
          <br />
        </p>
      </span>
    ),
  },
  content: {
    className: 'content2-content',
    children: (
      <span>
        <p>
          应用区块链技术，把合同及签署信息储存在链上。根据区块链信息的不可篡改性，对合同信息进行永久的保护。结合基于罚金的公平交换协议（COR协议），最大限度保证合同签署的公平性。
          <br />
        </p>
      </span>
    ),
  },
};
export const Content30DataSource = {
  wrapper: { className: 'home-page-wrapper content3-wrapper' },
  page: { className: 'home-page content3' },
  OverPack: { playScale: 0.3 },
  titleWrapper: {
    className: 'title-wrapper',
    children: [
      {
        name: 'title',
        children: (
          <span>
            <p>
              应用前景
              <br />
            </p>
          </span>
        ),
        className: 'title-h1',
      },
      {
        name: 'content',
        className: 'title-content kp0zkkmhw6o-editor_css',
        children: (
          <span>
            <p>
              <br />
            </p>
          </span>
        ),
      },
    ],
  },
  block: {
    className: 'content3-block-wrapper',
    children: [
      {
        name: 'block0',
        className: 'content3-block',
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: 'content3-icon',
            children:
              'https://zos.alipayobjects.com/rmsportal/ScHBSdwpTkAHZkJ.png',
          },
          textWrapper: { className: 'content3-text' },
          title: {
            className: 'content3-title',
            children: (
              <span>
                <p>企业人力资源管理</p>
              </span>
            ),
          },
          content: {
            className: 'content3-content',
            children: (
              <span>
                <p>进行日常人事文件的在线签署，实现无纸化办公与数字化转型。</p>
              </span>
            ),
          },
        },
      },
      {
        name: 'block1',
        className: 'content3-block',
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: 'content3-icon',
            children:
              'https://zos.alipayobjects.com/rmsportal/NKBELAOuuKbofDD.png',
          },
          textWrapper: { className: 'content3-text' },
          title: {
            className: 'content3-title',
            children: (
              <span>
                <p>B2B电商平台</p>
              </span>
            ),
          },
          content: {
            className: 'content3-content',
            children: (
              <span>
                <p>
                  使签约流程与交易流程无缝集成，让各类交易能够真正实现在线交易、时时交易。
                </p>
              </span>
            ),
          },
        },
      },
      {
        name: 'block2',
        className: 'content3-block',
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: 'content3-icon',
            children:
              'https://zos.alipayobjects.com/rmsportal/xMSBjgxBhKfyMWX.png',
          },
          textWrapper: { className: 'content3-text' },
          title: {
            className: 'content3-title',
            children: (
              <span>
                <p>房地产行业</p>
              </span>
            ),
          },
          content: {
            className: 'content3-content',
            children: (
              <span>
                <p>促进房地产的电子化，简化签约流程，缩短签署周期。</p>
              </span>
            ),
          },
        },
      },
      {
        name: 'block3',
        className: 'content3-block',
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: 'content3-icon',
            children:
              'https://zos.alipayobjects.com/rmsportal/MNdlBNhmDBLuzqp.png',
          },
          textWrapper: { className: 'content3-text' },
          title: {
            className: 'content3-title',
            children: (
              <span>
                <p>金融行业</p>
              </span>
            ),
          },
          content: {
            className: 'content3-content',
            children: (
              <span>
                <p>革新金融市场的合同签署模式，高效签约，快速推动业务增长。</p>
              </span>
            ),
          },
        },
      },
      {
        name: 'block4',
        className: 'content3-block',
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: 'content3-icon',
            children:
              'https://zos.alipayobjects.com/rmsportal/UsUmoBRyLvkIQeO.png',
          },
          textWrapper: { className: 'content3-text' },
          title: {
            className: 'content3-title',
            children: (
              <span>
                <p>医药行业</p>
              </span>
            ),
          },
          content: {
            className: 'content3-content',
            children: (
              <span>
                <p>
                  精准覆盖制药、流通、门店销售领域，标准化合同签署，实现多维度管控一体化。
                </p>
              </span>
            ),
          },
        },
      },
      {
        name: 'block5',
        className: 'content3-block',
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: 'content3-icon',
            children:
              'https://zos.alipayobjects.com/rmsportal/ipwaQLBLflRfUrg.png',
          },
          textWrapper: { className: 'content3-text' },
          title: {
            className: 'content3-title',
            children: (
              <span>
                <p>
                  物流行业
                  <br />
                </p>
              </span>
            ),
          },
          content: {
            className: 'content3-content',
            children: (
              <span>
                <p>
                  商家、快递公司与快递员三方共同签署合同，降低签约成本，提高签约效率。
                </p>
              </span>
            ),
          },
        },
      },
    ],
  },
};
export const Footer00DataSource = {
  wrapper: { className: 'home-page-wrapper footer0-wrapper' },
  OverPack: { className: 'home-page footer0', playScale: 0.05 },
  copyright: {
    className: 'copyright kp105rwsiag-editor_css',
    children: (
      <span>
        <span>©2021契安信</span>
      </span>
    ),
  },
};
