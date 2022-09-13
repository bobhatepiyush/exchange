<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="exchangeApp.client.home.createOrEditLabel"
          data-cy="ClientCreateUpdateHeading"
          v-text="$t('exchangeApp.client.home.createOrEditLabel')"
        >
          Create or edit a Client
        </h2>
        <div>
          <div class="form-group" v-if="client.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="client.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('exchangeApp.client.name')" for="client-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="client-name"
              data-cy="name"
              :class="{ valid: !$v.client.name.$invalid, invalid: $v.client.name.$invalid }"
              v-model="$v.client.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('exchangeApp.client.secretKey')" for="client-secretKey">Secret Key</label>
            <input
              type="text"
              class="form-control"
              name="secretKey"
              id="client-secretKey"
              data-cy="secretKey"
              :class="{ valid: !$v.client.secretKey.$invalid, invalid: $v.client.secretKey.$invalid }"
              v-model="$v.client.secretKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('exchangeApp.client.isActive')" for="client-isActive">Is Active</label>
            <input
              type="checkbox"
              class="form-check"
              name="isActive"
              id="client-isActive"
              data-cy="isActive"
              :class="{ valid: !$v.client.isActive.$invalid, invalid: $v.client.isActive.$invalid }"
              v-model="$v.client.isActive.$model"
            />
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
            :disabled="$v.client.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./client-update.component.ts"></script>
