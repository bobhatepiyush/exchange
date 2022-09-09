<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="channel">
        <h2 class="jh-entity-heading" data-cy="channelDetailsHeading">
          <span v-text="$t('exchangeApp.channel.detail.title')">Channel</span> {{ channel.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('exchangeApp.channel.name')">Name</span>
          </dt>
          <dd>
            <span>{{ channel.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.channel.homeLink')">Home Link</span>
          </dt>
          <dd>
            <span>{{ channel.homeLink }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.channel.secretKey')">Secret Key</span>
          </dt>
          <dd>
            <span>{{ channel.secretKey }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.channel.isActive')">Is Active</span>
          </dt>
          <dd>
            <span>{{ channel.isActive }}</span>
          </dd>
          <dt>
            <span v-text="$t('exchangeApp.channel.client')">Client</span>
          </dt>
          <dd>
            <div v-if="channel.client">
              <router-link :to="{ name: 'ClientView', params: { clientId: channel.client.id } }">{{ channel.client.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="channel.id" :to="{ name: 'ChannelEdit', params: { channelId: channel.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./channel-details.component.ts"></script>
