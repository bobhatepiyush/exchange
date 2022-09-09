<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="productContext">
        <h2 class="jh-entity-heading" data-cy="productContextDetailsHeading">
          <span v-text="$t('exchangeApp.productContext.detail.title')">ProductContext</span> {{ productContext.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('exchangeApp.productContext.l1')">L 1</span>
          </dt>
          <dd>
            <span>{{ productContext.l1 }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.productContext.l2')">L 2</span>
          </dt>
          <dd>
            <span>{{ productContext.l2 }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.productContext.l3')">L 3</span>
          </dt>
          <dd>
            <span>{{ productContext.l3 }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.productContext.contextId')">Context Id</span>
          </dt>
          <dd>
            <span>{{ productContext.contextId }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.productContext.isActive')">Is Active</span>
          </dt>
          <dd>
            <span>{{ productContext.isActive }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.productContext.client')">Client</span>
          </dt>
          <dd>
            <div v-if="productContext.client">
              <router-link :to="{ name: 'ChannelView', params: { channelId: productContext.client.id } }">{{
                productContext.client.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="productContext.id"
          :to="{ name: 'ProductContextEdit', params: { productContextId: productContext.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-context-details.component.ts"></script>
