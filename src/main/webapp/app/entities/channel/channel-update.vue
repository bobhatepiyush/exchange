<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="exchangeApp.channel.home.createOrEditLabel"
          data-cy="ChannelCreateUpdateHeading"
          v-text="$t('exchangeApp.channel.home.createOrEditLabel')"
        >
          Create or edit a Channel
        </h2>
        <div>
          <div class="form-group" v-if="channel.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="channel.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('exchangeApp.channel.name')" for="channel-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="channel-name"
              data-cy="name"
              :class="{ valid: !$v.channel.name.$invalid, invalid: $v.channel.name.$invalid }"
              v-model="$v.channel.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('exchangeApp.channel.homeLink')" for="channel-homeLink">Home Link</label>
            <input
              type="text"
              class="form-control"
              name="homeLink"
              id="channel-homeLink"
              data-cy="homeLink"
              :class="{ valid: !$v.channel.homeLink.$invalid, invalid: $v.channel.homeLink.$invalid }"
              v-model="$v.channel.homeLink.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('exchangeApp.channel.secretKey')" for="channel-secretKey">Secret Key</label>
            <input
              type="text"
              class="form-control"
              name="secretKey"
              id="channel-secretKey"
              data-cy="secretKey"
              :class="{ valid: !$v.channel.secretKey.$invalid, invalid: $v.channel.secretKey.$invalid }"
              v-model="$v.channel.secretKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('exchangeApp.channel.isActive')" for="channel-isActive">Is Active</label>
            <input
              type="checkbox"
              class="form-check"
              name="isActive"
              id="channel-isActive"
              data-cy="isActive"
              :class="{ valid: !$v.channel.isActive.$invalid, invalid: $v.channel.isActive.$invalid }"
              v-model="$v.channel.isActive.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('exchangeApp.channel.client')" for="channel-client">Client</label>
            <select class="form-control" id="channel-client" data-cy="client" name="client" v-model="channel.client">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="channel.client && clientOption.id === channel.client.id ? channel.client : clientOption"
                v-for="clientOption in clients"
                :key="clientOption.id"
              >
                {{ clientOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.channel.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./channel-update.component.ts"></script>
